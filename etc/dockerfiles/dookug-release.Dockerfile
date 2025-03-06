ARG WILDFLY_BASE_IMAGE
ARG ARTIFACT_DOWNLOADER_IMAGE

ARG DOWNLOAD_DIR_NAME="download"

################################################################################
# Default image customization
################################################################################
FROM ${WILDFLY_BASE_IMAGE} AS base

#COPY --chown=jboss:jboss etc/jboss-cli/*.cli $HOME/config
#COPY --chown=jboss:jboss etc/jboss-cli/embed-server.cli $HOME/config/embed-server.cli
#COPY --chown=jboss:jboss etc/jboss-cli/cli.properties $HOME/config

#RUN $WILDFLY_HOME/bin/jboss-cli.sh \
#        --properties=$HOME/config/cli.properties \
#        --file=$HOME/config/embed-server.cli \
#    && rm -rf $WILDFLY_HOME/standalone/configuration/standalone_xml_history/current/*


################################################################################
# Download .war
################################################################################
FROM ${ARTIFACT_DOWNLOADER_IMAGE} AS download
ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en'

ARG POM_GROUP_ID
ARG POM_ARTIFACT_ID
ARG POM_VERSION
ARG POM_EXTENSION

ENV NEXUS_OBJECT_GROUP_ID=$POM_GROUP_ID
ENV NEXUS_OBJECT_ARTIFACT_ID=$POM_ARTIFACT_ID
ENV NEXUS_OBJECT_VERSION=$POM_VERSION
ENV NEXUS_OBJECT_EXTENSION=$POM_EXTENSION

ENV NEXUS_DOWNLOAD_OUTPUT_FILE_NAME=$NEXUS_OBJECT_ARTIFACT_ID.$NEXUS_OBJECT_EXTENSION

RUN echo "DOCKER_SONATYPE_REPOSITORY=$SONATYPE_REPOSITORY" && \
    echo "DOWNLOAD_DIR=$DOWNLOAD_DIR" && \
    $HOME/script/sonatype-download.sh
################################################################################
# Create production image
################################################################################
FROM base AS prod

ARG POM_ARTIFACT_ID
ARG POM_VERSION
ARG POM_EXTENSION
ARG COFFEE_MODEL_BASE_JAVA_TIME_TIMEZONE_ID="UTC"

ARG DOWNLOAD_DIR_NAME
ARG DOWNLOAD_DIR=$HOME/$DOWNLOAD_DIR_NAME
ARG PROPERTIES_FILE

ENV LOGSTASH_MODULE_ID=$POM_ARTIFACT_ID
ENV LOGSTASH_MODULE_VERSION=$POM_VERSION
ENV JSON_MODULE_ID=$POM_ARTIFACT_ID
ENV JSON_MODULE_VERSION=$POM_VERSION

ENV HIBERNATE_DIALECT=$DEFAULT_PERSISTENCE_HIBERNATE_DIALECT
ENV COFFEE_MODEL_BASE_JAVA_TIME_TIMEZONE_ID="$COFFEE_MODEL_BASE_JAVA_TIME_TIMEZONE_ID"

ARG DEFAULT_FONT=Roboto
ARG DEFAULT_FONTS_DIRECTORY=etc/docker-compose/fonts
ARG DEFAULT_FOP_CONFIG_DIRECTORY=etc/docker-compose/fop-config
ARG DEFAULT_HANDLEBARS_DIRECTORY=etc/docker-compose/handlebars
ARG DEFAULT_PDFSIGN_DIRECTORY=etc/docker-compose/pdfsign
ARG DEFAULT_KEYS_DIRECTORY=etc/docker-compose/keys

ENV TARGET_FONT_DIRECTORY=${HOME}/fonts/${DEFAULT_FONT}
ENV TARGET_FOP_DIRECTORY=${HOME}/fop-config
ENV TARGET_HANDLEBARS_DIRECTORY=${HOME}/handlebars
ENV TARGET_PDFSIGN_DIRECTORY=${HOME}/pdfsign
ENV TARGET_KEYS_DIRECTORY=${HOME}/keys

LABEL moduleName="$POM_ARTIFACT_ID"
LABEL moduleVersion="$POM_VERSION"
LABEL defaultFont="$DEFAULT_FONT"

RUN echo "$HOME" && \
    echo "$DOWNLOAD_DIR/$OBJECT_DOWNLOAD_OUTPUT_FILE" && \
    echo "$POM_ARTIFACT_ID" && \
    echo "$POM_VERSION" && \
    mkdir -p ${TARGET_FONT_DIRECTORY} && \
    mkdir -p ${TARGET_FOP_DIRECTORY} && \
    mkdir -p ${TARGET_HANDLEBARS_DIRECTORY} && \
    mkdir -p ${TARGET_PDFSIGN_DIRECTORY} && \
    mkdir -p ${TARGET_KEYS_DIRECTORY} && \
    # Adding deployment artifact as ROOT.war and prepare to auto deploy
    touch ${WILDFLY_HOME}/standalone/deployments/ROOT.war.dodeploy

#COPY --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP $PROPERTIES_FILE $WILDFLY_PROPERTIES_FILE
COPY --from=download --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP $DOWNLOAD_DIR/*.$POM_EXTENSION $WILDFLY_HOME/standalone/deployments/ROOT.war
# Copy default fonts
COPY --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP ${DEFAULT_FONTS_DIRECTORY}/${DEFAULT_FONT}/*.* ${TARGET_FONT_DIRECTORY}
# Copy fop-config
COPY --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP ${DEFAULT_FOP_CONFIG_DIRECTORY}/*.* ${TARGET_FOP_DIRECTORY}
# Handlebars default helper
COPY --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP ${DEFAULT_HANDLEBARS_DIRECTORY}/ ${TARGET_HANDLEBARS_DIRECTORY}
# pdfsign-default signature
COPY --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP ${DEFAULT_PDFSIGN_DIRECTORY}/ ${TARGET_PDFSIGN_DIRECTORY}
# pdfsign-default keys
COPY --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP ${DEFAULT_KEYS_DIRECTORY}/pdf/keystore.p12 ${TARGET_KEYS_DIRECTORY}/keystore.p12
