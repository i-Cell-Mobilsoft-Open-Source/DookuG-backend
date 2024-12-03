# docker-compose adja
ARG WILDFLY_BASE_IMAGE
ARG ARTIFACT_DOWNLOADER_IMAGE

ARG COFFEE_MODEL_BASE_JAVA_TIME_TIMEZONE_ID="UTC"
ARG DOWNLOAD_DIR_NAME="download"

################################################################################
# Default image customization
################################################################################
FROM ${WILDFLY_BASE_IMAGE} as base

# Minta kiindulo pontnak
#COPY --chown=jboss:jboss etc/docker-compose/jboss-cli/*.cli $HOME/config
#COPY --chown=jboss:jboss etc/docker-compose/jboss-cli/embed-server.cli $HOME/config/embed-server.cli
#COPY --chown=jboss:jboss etc/docker-compose/jboss-cli/cli.properties $HOME/config

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

RUN echo "DOWNLOAD_DIR=$DOWNLOAD_DIR" && \
    ${HOME}/script/maven-search-download.sh

################################################################################
# Create production image
################################################################################
FROM base as prod

ARG POM_ARTIFACT_ID
ARG POM_VERSION
ARG POM_EXTENSION

ARG DOWNLOAD_DIR_NAME
ARG DOWNLOAD_DIR=$HOME/$DOWNLOAD_DIR_NAME
ARG PROPERTIES_FILE

ENV LOGSTASH_MODULE_ID="$POM_ARTIFACT_ID"
ENV LOGSTASH_MODULE_VERSION="$POM_VERSION"

ENV HIBERNATE_DIALECT=$DEFAULT_PERSISTENCE_HIBERNATE_DIALECT
ENV COFFEE_MODEL_BASE_JAVA_TIME_TIMEZONE_ID="$COFFEE_MODEL_BASE_JAVA_TIME_TIMEZONE_ID"

ARG DEFAULT_FONTS_DIRECTORY=etc/docker-compose/fonts
ARG DEFAULT_FONT=Roboto
ARG DEFAULT_FOP_CONFIG_DIRECTORY=etc/docker-compose/fop-config
ARG DEFAULT_HANDLEBARS_DIRECTORY=etc/docker-compose/handlebars
ARG DEFAULT_PDFSIGN_DIRECTORY=etc/docker-compose/pdfsign

ENV TARGET_FONT_DIRECTORY=${HOME}/fonts/${DEFAULT_FONT}
ENV TARGET_FOP_DIRECTORY=${HOME}/fop-config
ENV TARGET_HANDLEBARS_DIRECTORY=${HOME}/handlebars
ENV TARGET_PDFSIGN_DIRECTORY=${HOME}/pdfsign

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
    # Adding deployment artifact as ROOT.war and prepare to auto deploy
    touch ${WILDFLY_HOME}/standalone/deployments/ROOT.war.dodeploy

#COPY --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP $PROPERTIES_FILE $WILDFLY_PROPERTIES_FILE
COPY --from=download --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP $DOWNLOAD_DIR/*.$POM_EXTENSION $WILDFLY_HOME/standalone/deployments/ROOT.war
# Copy default Roboto fonts
COPY --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP ${DEFAULT_FONTS_DIRECTORY}/${DEFAULT_FONT}/*.* ${TARGET_FONT_DIRECTORY}
# Copy fop-config
COPY --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP ${DEFAULT_FOP_CONFIG_DIRECTORY}/*.* ${TARGET_FOP_DIRECTORY}
# Handlebars default helper
COPY --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP ${DEFAULT_HANDLEBARS_DIRECTORY}/ ${TARGET_HANDLEBARS_DIRECTORY}
# pdfsign-default signature
COPY --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP ${DEFAULT_PDFSIGN_DIRECTORY}/ ${TARGET_PDFSIGN_DIRECTORY}
COPY --chown=$SYSTEM_USER:$SYSTEM_USER_GROUP /etc/docker-compose/keys/pdf/keystore.p12 ${HOME}/keys/keystore.p12
