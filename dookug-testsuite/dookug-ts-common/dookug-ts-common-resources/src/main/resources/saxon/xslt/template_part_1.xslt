    <xsl:template match="n1:invoice">
        <xsl:variable name="CurrencyCodeValue" select="n1:invoiceHead/n1:invoiceDetail/n1:currencyCode"/>
        <xsl:variable name="ProductFeeSummaryCount" select="count(n1:productFeeSummary)"/>
        <xsl:if test="exists(n1:invoiceReference)">
            <xsl:for-each select="n1:invoiceReference">
                <fo:block margin-right="100% - 100%" space-before="0" space-after="0" margin="0pt">
                    <xsl:call-template name="OriginalInvoiceNumberTemplate_L10N"/>
                    <xsl:for-each select="n1:originalInvoiceNumber">
                        <altova:inline-container-substitute font-weight="bold">
                            <xsl:apply-templates/>
                        </altova:inline-container-substitute>
                    </xsl:for-each>
                </fo:block>
                <fo:block margin-right="100% - 100%" space-before="0" space-after="0" margin="0pt">
                    <xsl:call-template name="ModificationIndexTemplate_L10N"/>
                    <xsl:for-each select="n1:modificationIndex">
                        <altova:inline-container-substitute font-weight="bold">
                            <xsl:apply-templates/>
                        </altova:inline-container-substitute>
                    </xsl:for-each>
                </fo:block>
            </xsl:for-each>
        </xsl:if>
        <xsl:for-each select="n1:invoiceHead">
            <fo:block padding-bottom="5mm" padding-top="5mm" margin-right="100% - 100%" space-before="0" space-after="0" margin="0pt">
                <xsl:variable name="altova:table">
                    <fo:table border-collapse="collapse" border-style="none" table-layout="fixed" width="100%" border="solid 1pt gray">
                        <fo:table-column column-width="85.500mm"/>
                        <fo:table-column column-width="19mm"/>
                        <fo:table-column column-width="85.500mm"/>
                        <xsl:variable name="altova:CurrContextGrid_3" select="."/>
                        <xsl:variable name="altova:ColumnData"/>
                        <fo:table-body start-indent="0pt">
                            <fo:table-row font-size="10pt">
                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                    <fo:block text-align="center">
                                        <xsl:call-template name="SellerTemplate_L10N"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                    <fo:block text-align="center"/>
                                </fo:table-cell>
                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                    <fo:block text-align="center">
                                        <xsl:call-template name="BuyerTemplate_L10N"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <fo:table-row>
                                <fo:table-cell border-color="black" border-style="solid" border="solid 1pt gray" padding="2pt" display-align="before">
                                    <fo:block>
                                        <xsl:for-each select="n1:supplierInfo">
                                            <altova:line-break/>
                                            <xsl:choose>
                                                <xsl:when test="not(exists(n1:groupMemberTaxNumber))">
                                                    <xsl:call-template name="TaxNumberTemplate_L10N"/>
                                                </xsl:when>
                                                <xsl:when test="exists(n1:groupMemberTaxNumber)">
                                                    <xsl:call-template name="TaxNumberGroupTemplate_L10N"/>
                                                </xsl:when>
                                            </xsl:choose>
                                            <xsl:for-each select="n1:supplierTaxNumber">
                                                <xsl:call-template name="BaseTaxNumberTemplate"/>
                                            </xsl:for-each>
                                            <xsl:if test="exists(n1:communityVatNumber)">
                                                <altova:line-break/>
                                                <xsl:call-template name="CommunityVatNumberTemplate_L10N"/>
                                                <xsl:for-each select="n1:communityVatNumber">
                                                    <altova:inline-container-substitute font-weight="bold">
                                                        <xsl:apply-templates/>
                                                    </altova:inline-container-substitute>
                                                </xsl:for-each>
                                            </xsl:if>
                                            <xsl:if test="exists(n1:groupMemberTaxNumber)">
                                                <altova:line-break/>
                                                <xsl:call-template name="GroupMemberTaxNumberTemplate_L10N"/>
                                                <xsl:for-each select="n1:groupMemberTaxNumber">
                                                    <xsl:call-template name="BaseTaxNumberTemplate"/>
                                                </xsl:for-each>
                                            </xsl:if>
                                            <xsl:if test="exists(n1:exciseLicenceNum)">
                                                <altova:line-break/>
                                                <xsl:call-template name="ExciseLicensNumberTemplate_L10N"/>
                                                <xsl:for-each select="n1:exciseLicenceNum">
                                                    <altova:inline-container-substitute font-weight="bold">
                                                        <xsl:apply-templates/>
                                                    </altova:inline-container-substitute>
                                                </xsl:for-each>
                                            </xsl:if>
                                            <altova:line-break/>
                                            <altova:line-break/>
                                            <xsl:call-template name="InvoiceHeadNameTemplate_L10N"/>
                                            <xsl:for-each select="n1:supplierName">
                                                <altova:inline-container-substitute font-weight="bold">
                                                    <xsl:apply-templates/>
                                                </altova:inline-container-substitute>
                                            </xsl:for-each>
                                            <xsl:if test="../n1:invoiceDetail/n1:smallBusinessIndicator = true()">
                                                <fo:inline>
                                                    <xsl:text>&#160;</xsl:text>
                                                </fo:inline>
                                                <xsl:call-template name="SmallBusinessIndicatorTemplate_L10N"/>
                                            </xsl:if>
                                            <xsl:if test="n1:individualExemption = true()">
                                                <fo:inline>
                                                    <xsl:text>&#160;</xsl:text>
                                                </fo:inline>
                                                <xsl:call-template name="IndividualExemptionTemplate_L10N"/>
                                            </xsl:if>
                                            <altova:line-break/>
                                            <xsl:call-template name="InvoiceHeadAddressTemplate_L10N"/>
                                            <xsl:for-each select="n1:supplierAddress">
                                                <xsl:call-template name="BaseAddressTemplate"/>
                                            </xsl:for-each>
                                            <xsl:if test="exists(n1:supplierBankAccountNumber)">
                                                <altova:line-break/>
                                                <xsl:call-template name="BankAccountNumberTemplate_L10N"/>
                                                <xsl:for-each select="n1:supplierBankAccountNumber">
                                                    <altova:inline-container-substitute font-weight="bold">
                                                        <xsl:apply-templates/>
                                                    </altova:inline-container-substitute>
                                                </xsl:for-each>
                                            </xsl:if>
                                        </xsl:for-each>
                                        <altova:line-break/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                    <fo:block/>
                                </fo:table-cell>
                                <fo:table-cell border-color="black" border-style="solid" border="solid 1pt gray" padding="2pt" display-align="before">
                                    <fo:block>
                                        <xsl:for-each select="n1:customerInfo">
                                            <altova:line-break/>
                                            <xsl:choose>
                                                <xsl:when test="n1:customerVatStatus != &apos;PRIVATE_PERSON&apos; and exists(n1:customerVatData/n1:customerTaxNumber)">
                                                    <xsl:choose>
                                                        <xsl:when test="not(exists(n1:customerVatData/n1:customerTaxNumber/n1:groupMemberTaxNumber))">
                                                            <xsl:call-template name="TaxNumberTemplate_L10N"/>
                                                        </xsl:when>
                                                        <xsl:when test="exists(n1:customerVatData/n1:customerTaxNumber/n1:groupMemberTaxNumber)">
                                                            <xsl:call-template name="TaxNumberGroupTemplate_L10N"/>
                                                        </xsl:when>
                                                    </xsl:choose>
                                                    <xsl:for-each select="n1:customerVatData">
                                                        <xsl:for-each select="n1:customerTaxNumber">
                                                            <xsl:call-template name="BaseTaxNumberTemplate"/>
                                                            <xsl:if test="exists(n1:groupMemberTaxNumber)">
                                                                <altova:line-break/>
                                                                <xsl:call-template name="GroupMemberTaxNumberTemplate_L10N"/>
                                                                <xsl:for-each select="n1:groupMemberTaxNumber">
                                                                    <xsl:call-template name="BaseTaxNumberTemplate"/>
                                                                </xsl:for-each>
                                                            </xsl:if>
                                                            <altova:line-break/>
                                                        </xsl:for-each>
                                                    </xsl:for-each>
                                                    <altova:line-break/>
                                                    <xsl:if test="exists(n1:customerName)">
                                                        <xsl:call-template name="InvoiceHeadNameTemplate_L10N"/>
                                                        <xsl:for-each select="n1:customerName">
                                                            <altova:inline-container-substitute font-weight="bold">
                                                                <xsl:apply-templates/>
                                                            </altova:inline-container-substitute>
                                                        </xsl:for-each>
                                                    </xsl:if>
                                                    <altova:line-break/>
                                                    <xsl:if test="exists(n1:customerAddress)">
                                                        <xsl:call-template name="InvoiceHeadAddressTemplate_L10N"/>
                                                        <xsl:for-each select="n1:customerAddress">
                                                            <xsl:call-template name="BaseAddressTemplate"/>
                                                        </xsl:for-each>
                                                    </xsl:if>
                                                </xsl:when>
                                                <xsl:when test="n1:customerVatStatus != &apos;PRIVATE_PERSON&apos; and exists(n1:customerVatData/n1:communityVatNumber)">
                                                    <xsl:call-template name="CommunityVatNumberTemplate_L10N"/>
                                                    <xsl:for-each select="n1:customerVatData">
                                                        <xsl:for-each select="n1:communityVatNumber">
                                                            <altova:inline-container-substitute font-weight="bold">
                                                                <xsl:apply-templates/>
                                                            </altova:inline-container-substitute>
                                                        </xsl:for-each>
                                                    </xsl:for-each>
                                                    <altova:line-break/>
                                                    <altova:line-break/>
                                                    <xsl:if test="exists(n1:customerName)">
                                                        <xsl:call-template name="InvoiceHeadNameTemplate_L10N"/>
                                                        <xsl:for-each select="n1:customerName">
                                                            <altova:inline-container-substitute font-weight="bold">
                                                                <xsl:apply-templates/>
                                                            </altova:inline-container-substitute>
                                                        </xsl:for-each>
                                                    </xsl:if>
                                                    <altova:line-break/>
                                                    <xsl:if test="exists(n1:customerAddress)">
                                                        <xsl:call-template name="InvoiceHeadAddressTemplate_L10N"/>
                                                        <xsl:for-each select="n1:customerAddress">
                                                            <xsl:call-template name="BaseAddressTemplate"/>
                                                        </xsl:for-each>
                                                    </xsl:if>
                                                </xsl:when>
                                                <xsl:when test="n1:customerVatStatus != &apos;PRIVATE_PERSON&apos; and exists(n1:customerVatData/n1:thirdStateTaxId)">
                                                    <xsl:call-template name="ThirdStateTaxIdTemplate_L10N"/>
                                                    <xsl:for-each select="n1:customerVatData">
                                                        <xsl:for-each select="n1:thirdStateTaxId">
                                                            <altova:inline-container-substitute font-weight="bold">
                                                                <xsl:apply-templates/>
                                                            </altova:inline-container-substitute>
                                                        </xsl:for-each>
                                                    </xsl:for-each>
                                                    <altova:line-break/>
                                                    <altova:line-break/>
                                                    <xsl:if test="exists(n1:customerName)">
                                                        <xsl:call-template name="InvoiceHeadNameTemplate_L10N"/>
                                                        <xsl:for-each select="n1:customerName">
                                                            <altova:inline-container-substitute font-weight="bold">
                                                                <xsl:apply-templates/>
                                                            </altova:inline-container-substitute>
                                                        </xsl:for-each>
                                                    </xsl:if>
                                                    <altova:line-break/>
                                                    <xsl:if test="exists(n1:customerAddress)">
                                                        <xsl:call-template name="InvoiceHeadAddressTemplate_L10N"/>
                                                        <xsl:for-each select="n1:customerAddress">
                                                            <xsl:call-template name="BaseAddressTemplate"/>
                                                        </xsl:for-each>
                                                    </xsl:if>
                                                </xsl:when>
                                                <xsl:when test="n1:customerVatStatus = &apos;PRIVATE_PERSON&apos;">
                                                    <xsl:call-template name="PrivatePersonTemplate_L10N"/>
                                                    <altova:line-break/>
                                                </xsl:when>
                                            </xsl:choose>
                                            <xsl:if test="exists(n1:customerBankAccountNumber)">
                                                <altova:line-break/>
                                                <xsl:call-template name="BankAccountNumberTemplate_L10N"/>
                                                <xsl:for-each select="n1:customerBankAccountNumber">
                                                    <altova:inline-container-substitute font-weight="bold">
                                                        <xsl:apply-templates/>
                                                    </altova:inline-container-substitute>
                                                </xsl:for-each>
                                            </xsl:if>
                                        </xsl:for-each>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                            <xsl:choose>
                                <xsl:when test="exists(n1:fiscalRepresentativeInfo)">
                                    <fo:table-row>
                                        <fo:table-cell border-color="black" border-style="solid" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:for-each select="n1:fiscalRepresentativeInfo">
                                                    <xsl:call-template name="FiscalRepresentativeNameTemplate_L10N"/>
                                                    <xsl:for-each select="n1:fiscalRepresentativeName">
                                                        <altova:inline-container-substitute font-weight="bold">
                                                            <xsl:apply-templates/>
                                                        </altova:inline-container-substitute>
                                                    </xsl:for-each>
                                                    <altova:line-break/>
                                                    <xsl:call-template name="TaxNumberTemplate_L10N"/>
                                                    <xsl:for-each select="n1:fiscalRepresentativeTaxNumber">
                                                        <xsl:call-template name="BaseTaxNumberTemplate"/>
                                                    </xsl:for-each>
                                                    <altova:line-break/>
                                                    <xsl:call-template name="InvoiceHeadAddressTemplate_L10N"/>
                                                    <xsl:for-each select="n1:fiscalRepresentativeAddress">
                                                        <xsl:call-template name="BaseAddressTemplate"/>
                                                    </xsl:for-each>
                                                    <xsl:if test="exists(n1:fiscalRepresentativeBankAccountNumber)">
                                                        <xsl:call-template name="BankAccountNumberTemplate_L10N"/>
                                                        <xsl:for-each select="n1:fiscalRepresentativeBankAccountNumber">
                                                            <altova:inline-container-substitute font-weight="bold">
                                                                <xsl:apply-templates/>
                                                            </altova:inline-container-substitute>
                                                        </xsl:for-each>
                                                    </xsl:if>
                                                </xsl:for-each>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block/>
                                        </fo:table-cell>
                                        <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block/>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:when>
                                <xsl:otherwise/>
                            </xsl:choose>
                        </fo:table-body>
                    </fo:table>
                </xsl:variable>
                <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
            </fo:block>
            <xsl:for-each select="n1:invoiceDetail">
                <fo:block margin-right="100% - 100%" space-before="0" space-after="0" margin="0pt">
                    <xsl:variable name="altova:table">
                        <fo:table border-collapse="collapse" border-style="none" table-layout="fixed" width="100%" border="solid 1pt gray">
                            <fo:table-column column-width="62.700mm"/>
                            <fo:table-column column-width="62.700mm"/>
                            <fo:table-column column-width="62.700mm"/>
                            <xsl:variable name="altova:CurrContextGrid_4" select="."/>
                            <xsl:variable name="altova:ColumnData"/>
                            <fo:table-body start-indent="0pt">
                                <fo:table-row>
                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                        <fo:block>
                                            <xsl:call-template name="InvoiceCategoryTemplate"/>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                        <fo:block>
                                            <xsl:call-template name="InvoiceAppearanceTemplate"/>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                        <fo:block>
                                            <xsl:if test="n1:selfBillingIndicator = true()">
                                                <xsl:call-template name="SelfBillingIncidatorTemplate_L10N"/>
                                            </xsl:if>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                        <fo:block>
                                            <xsl:call-template name="InvoiceDeliveryDateTemplate_L10N"/>
                                            <xsl:choose>
                                                <xsl:when test="n1:invoiceCategory != &apos;AGGREGATE&apos;">
                                                    <xsl:for-each select="n1:invoiceDeliveryDate">
                                                        <xsl:call-template name="GenericDateOffset_Inner"/>
                                                    </xsl:for-each>
                                                </xsl:when>
                                                <xsl:when test="n1:invoiceCategory = &apos;AGGREGATE&apos;">
                                                    <xsl:call-template name="AggregateInstructionsTemplate_L10N"/>
                                                </xsl:when>
                                            </xsl:choose>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                        <fo:block>
                                            <xsl:call-template name="InvoiceAccountingDeliveryDateTemplate_L10N"/>
                                            <xsl:for-each select="n1:invoiceAccountingDeliveryDate">
                                                <xsl:call-template name="GenericDateOffset_Inner"/>
                                            </xsl:for-each>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                        <fo:block>
                                            <xsl:if test="n1:cashAccountingIndicator = true()">
                                                <xsl:call-template name="CashAccountingIndicatorTemplate_L10N"/>
                                            </xsl:if>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:choose>
                                    <xsl:when test="exists(n1:invoiceDeliveryPeriodStart) or exists(n1:invoiceDeliveryPeriodEnd) or n1:periodicalSettlement = true()">
                                        <fo:table-row>
                                            <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <xsl:call-template name="InvoiceDeliveryPeriodStartTemplate_L10N"/>
                                                    <xsl:for-each select="n1:invoiceDeliveryPeriodStart">
                                                        <xsl:call-template name="GenericDateOffset_Inner"/>
                                                    </xsl:for-each>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <xsl:call-template name="InvoiceDeliveryPeriodEndTemplate_L10N"/>
                                                    <xsl:for-each select="n1:invoiceDeliveryPeriodEnd">
                                                        <xsl:call-template name="GenericDateOffset_Inner"/>
                                                    </xsl:for-each>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <xsl:if test="n1:periodicalSettlement = true()">
                                                        <xsl:call-template name="PeriodicalSettlementTemplate_L10N"/>
                                                    </xsl:if>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </xsl:when>
                                    <xsl:otherwise/>
                                </xsl:choose>
                                <fo:table-row>
                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                        <fo:block>
                                            <xsl:call-template name="PaymentMethodTemplate_C5X"/>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                        <fo:block>
                                            <xsl:call-template name="PaymentDateTemplate_L10N"/>
                                            <xsl:for-each select="n1:paymentDate">
                                                <xsl:call-template name="GenericDateOffset_Inner"/>
                                            </xsl:for-each>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell number-rows-spanned="2" border-style="none" border="solid 1pt gray" padding="2pt" display-align="before">
                                        <fo:block>
                                            <xsl:if test="n1:utilitySettlementIndicator = true()">
                                                <xsl:call-template name="UtilitySettlementIndicatorTemplate_L10N"/>
                                            </xsl:if>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                        <fo:block>
                                            <xsl:call-template name="CurrencyCodeTemplate_L10N"/>
                                            <xsl:for-each select="n1:currencyCode">
                                                <altova:inline-container-substitute font-weight="bold">
                                                    <xsl:apply-templates/>
                                                </altova:inline-container-substitute>
                                            </xsl:for-each>
                                        </fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                        <fo:block>
                                            <xsl:call-template name="ExchangeRateTemplate_L10N"/>
                                            <xsl:choose>
                                                <xsl:when test="n1:invoiceCategory != &apos;AGGREGATE&apos;">
                                                    <xsl:for-each select="n1:exchangeRate">
                                                        <xsl:call-template name="GenericExchangeRateOffset"/>
                                                    </xsl:for-each>
                                                </xsl:when>
                                                <xsl:when test="n1:invoiceCategory = &apos;AGGREGATE&apos;">
                                                    <xsl:call-template name="AggregateInstructionsTemplate_L10N"/>
                                                </xsl:when>
                                            </xsl:choose>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>
                    </xsl:variable>
                    <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                </fo:block>
                <fo:block padding-top="5mm" margin-right="100% - 100%" space-before="0" space-after="0" margin="0pt">
                    <xsl:if test="exists(n1:conventionalInvoiceInfo/n1:orderNumbers)">
                        <xsl:variable name="altova:table">
                            <fo:table border-collapse="collapse" border-style="none" table-layout="fixed" width="100%" border="solid 1pt gray" altova:hide-rows="body-empty" altova:hide-cols="body-empty">
                                <fo:table-column column-width="62.700mm"/>
                                <fo:table-column column-width="127.300mm"/>
                                <xsl:variable name="altova:CurrContextGrid_5" select="."/>
                                <xsl:variable name="altova:ColumnData"/>
                                <fo:table-body start-indent="0pt">
                                    <xsl:for-each select="n1:conventionalInvoiceInfo">
                                        <xsl:for-each select="n1:orderNumbers">
                                            <xsl:for-each select="n1:orderNumber">
                                                <fo:table-row>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center" altova:is-body-cell="true">
                                                        <fo:block>
                                                            <xsl:if test="position() &lt; 2">
                                                                <xsl:call-template name="ConventionalInfoOrderNumberTemplate_L10N"/>
                                                            </xsl:if>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center" altova:is-body-cell="true">
                                                        <fo:block>
                                                            <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>
                                            </xsl:for-each>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </xsl:variable>
                        <xsl:variable name="altova:effective-table">
                            <xsl:variable name="altova:col-count" select="sum( for $altova:cell in $altova:table/fo:table/(fo:table-header | fo:table-body | fo:table-footer)[ 1 ]/fo:table-row[ 1 ]/fo:table-cell return altova:col-span( $altova:cell ) )"/>
                            <xsl:variable name="altova:TableIndexInfo" select="altova:BuildTableIndexInfo($altova:table)"/>
                            <xsl:variable name="altova:generate-cols" as="xs:boolean*">
                                <xsl:choose>
                                    <xsl:when test="$altova:table/fo:table/@altova:hide-cols = 'empty'">
                                        <xsl:sequence select="for $altova:pos in 1 to $altova:col-count return some $altova:cell in $altova:table/fo:table/(fo:table-header | fo:table-body | fo:table-footer)/fo:table-row/fo:table-cell[ altova:col-position(., $altova:TableIndexInfo) = $altova:pos ] satisfies not( altova:is-cell-empty( $altova:cell ) )"/>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:sequence select="for $altova:pos in 1 to $altova:col-count return some $altova:cell in $altova:table/fo:table/fo:table-body/fo:table-row/fo:table-cell[ altova:col-position(., $altova:TableIndexInfo) = $altova:pos ] satisfies not( altova:is-cell-empty( $altova:cell ) )"/>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </xsl:variable>
                            <xsl:apply-templates select="$altova:table" mode="altova:generate-table">
                                <xsl:with-param name="altova:generate-cols" select="$altova:generate-cols"/>
                                <xsl:with-param name="altova:TableIndexInfo" select="$altova:TableIndexInfo"/>
                            </xsl:apply-templates>
                        </xsl:variable>
                        <xsl:apply-templates select="$altova:effective-table" mode="altova:copy-table"/>
                    </xsl:if>
                    <xsl:if test="exists(n1:conventionalInvoiceInfo/n1:deliveryNotes)">
                        <xsl:variable name="altova:table">
                            <fo:table border-collapse="collapse" border-style="none" table-layout="fixed" width="100%" border="solid 1pt gray">
                                <fo:table-column column-width="62.700mm"/>
                                <fo:table-column column-width="127.300mm"/>
                                <xsl:variable name="altova:CurrContextGrid_6" select="."/>
                                <xsl:variable name="altova:ColumnData"/>
                                <fo:table-body start-indent="0pt">
                                    <xsl:for-each select="n1:conventionalInvoiceInfo">
                                        <xsl:for-each select="n1:deliveryNotes">
                                            <xsl:for-each select="n1:deliveryNote">
                                                <fo:table-row>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:if test="position() &lt; 2">
                                                                <xsl:call-template name="ConventionalInfoDeliveryNoteTemplate_L10N"/>
                                                            </xsl:if>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>
                                            </xsl:for-each>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </xsl:variable>
                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                    </xsl:if>
                    <xsl:if test="exists(n1:conventionalInvoiceInfo/n1:shippingDates)">
                        <xsl:variable name="altova:table">
                            <fo:table border-collapse="collapse" border-style="none" table-layout="fixed" width="100%" border="solid 1pt gray">
                                <fo:table-column column-width="62.700mm"/>
                                <fo:table-column column-width="127.300mm"/>
                                <xsl:variable name="altova:CurrContextGrid_7" select="."/>
                                <xsl:variable name="altova:ColumnData"/>
                                <fo:table-body start-indent="0pt">
                                    <xsl:for-each select="n1:conventionalInvoiceInfo">
                                        <xsl:for-each select="n1:shippingDates">
                                            <xsl:for-each select="n1:shippingDate">
                                                <fo:table-row>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:if test="position() &lt; 2">
                                                                <xsl:call-template name="ConventionalInfoShippingDateTemplate_L10N"/>
                                                            </xsl:if>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>
                                            </xsl:for-each>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </xsl:variable>
                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                    </xsl:if>
                    <xsl:if test="exists(n1:conventionalInvoiceInfo/n1:contractNumbers)">
                        <xsl:variable name="altova:table">
                            <fo:table border-collapse="collapse" border-style="none" table-layout="fixed" width="100%" border="solid 1pt gray">
                                <fo:table-column column-width="62.700mm"/>
                                <fo:table-column column-width="127.300mm"/>
                                <xsl:variable name="altova:CurrContextGrid_8" select="."/>
                                <xsl:variable name="altova:ColumnData"/>
                                <fo:table-body start-indent="0pt">
                                    <xsl:for-each select="n1:conventionalInvoiceInfo">
                                        <xsl:for-each select="n1:contractNumbers">
                                            <xsl:for-each select="n1:contractNumber">
                                                <fo:table-row>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:if test="position() &lt; 2">
                                                                <xsl:call-template name="ConventionalInfoContractNumberTemplate_L10N"/>
                                                            </xsl:if>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>
                                            </xsl:for-each>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </xsl:variable>
                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                    </xsl:if>
                    <xsl:if test="exists(n1:conventionalInvoiceInfo/n1:supplierCompanyCodes)">
                        <xsl:variable name="altova:table">
                            <fo:table border-collapse="collapse" border-style="none" table-layout="fixed" width="100%" border="solid 1pt gray">
                                <fo:table-column column-width="62.700mm"/>
                                <fo:table-column column-width="127.300mm"/>
                                <xsl:variable name="altova:CurrContextGrid_9" select="."/>
                                <xsl:variable name="altova:ColumnData"/>
                                <fo:table-body start-indent="0pt">
                                    <xsl:for-each select="n1:conventionalInvoiceInfo">
                                        <xsl:for-each select="n1:supplierCompanyCodes">
                                            <xsl:for-each select="n1:supplierCompanyCode">
                                                <fo:table-row>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:if test="position() &lt; 2">
                                                                <xsl:call-template name="ConventionalInfoSupplierCompanyCodeTemplate_L10N"/>
                                                            </xsl:if>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>
                                            </xsl:for-each>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </xsl:variable>
                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                    </xsl:if>
                    <xsl:if test="exists(n1:conventionalInvoiceInfo/n1:customerCompanyCodes)">
                        <xsl:variable name="altova:table">
                            <fo:table border-collapse="collapse" border-style="none" table-layout="fixed" width="100%" border="solid 1pt gray">
                                <fo:table-column column-width="62.700mm"/>
                                <fo:table-column column-width="127.300mm"/>
                                <xsl:variable name="altova:CurrContextGrid_10" select="."/>
                                <xsl:variable name="altova:ColumnData"/>
                                <fo:table-body start-indent="0pt">
                                    <xsl:for-each select="n1:conventionalInvoiceInfo">
                                        <xsl:for-each select="n1:customerCompanyCodes">
                                            <xsl:for-each select="n1:customerCompanyCode">
                                                <fo:table-row>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:if test="position() &lt; 2">
                                                                <xsl:call-template name="ConventionalInfoCustomerCompanyCodeTemplate_L10N"/>
                                                            </xsl:if>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>
                                            </xsl:for-each>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </xsl:variable>
                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                    </xsl:if>
                    <xsl:if test="exists(n1:conventionalInvoiceInfo/n1:dealerCodes)">
                        <xsl:variable name="altova:table">
                            <fo:table border-collapse="collapse" border-style="none" table-layout="fixed" width="100%" border="solid 1pt gray">
                                <fo:table-column column-width="62.700mm"/>
                                <fo:table-column column-width="127.300mm"/>
                                <xsl:variable name="altova:CurrContextGrid_11" select="."/>
                                <xsl:variable name="altova:ColumnData"/>
                                <fo:table-body start-indent="0pt">
                                    <xsl:for-each select="n1:conventionalInvoiceInfo">
                                        <xsl:for-each select="n1:dealerCodes">
                                            <xsl:for-each select="n1:dealerCode">
                                                <fo:table-row>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:if test="position() &lt; 2">
                                                                <xsl:call-template name="ConventionalInfoDealerCodeTemplate_L10N"/>
                                                            </xsl:if>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>
                                            </xsl:for-each>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </xsl:variable>
                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                    </xsl:if>
                    <xsl:if test="exists(n1:conventionalInvoiceInfo/n1:costCenters)">
                        <xsl:variable name="altova:table">
                            <fo:table border-collapse="collapse" border-style="none" table-layout="fixed" width="100%" border="solid 1pt gray">
                                <fo:table-column column-width="62.700mm"/>
                                <fo:table-column column-width="127.300mm"/>
                                <xsl:variable name="altova:CurrContextGrid_12" select="."/>
                                <xsl:variable name="altova:ColumnData"/>
                                <fo:table-body start-indent="0pt">
                                    <xsl:for-each select="n1:conventionalInvoiceInfo">
                                        <xsl:for-each select="n1:costCenters">
                                            <xsl:for-each select="n1:costCenter">
                                                <fo:table-row>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:if test="position() &lt; 2">
                                                                <xsl:call-template name="ConventionalInfoCostCenterTemplate_L10N"/>
                                                            </xsl:if>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>
                                            </xsl:for-each>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </xsl:variable>
                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                    </xsl:if>
                    <xsl:if test="exists(n1:conventionalInvoiceInfo/n1:projectNumbers)">
                        <xsl:variable name="altova:table">
                            <fo:table border-collapse="collapse" border-style="none" table-layout="fixed" width="100%" border="solid 1pt gray">
                                <fo:table-column column-width="62.700mm"/>
                                <fo:table-column column-width="127.300mm"/>
                                <xsl:variable name="altova:CurrContextGrid_13" select="."/>
                                <xsl:variable name="altova:ColumnData"/>
                                <fo:table-body start-indent="0pt">
                                    <xsl:for-each select="n1:conventionalInvoiceInfo">
                                        <xsl:for-each select="n1:projectNumbers">
                                            <xsl:for-each select="n1:projectNumber">
                                                <fo:table-row>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:if test="position() &lt; 2">
                                                                <xsl:call-template name="ConventionalInfoProjectNumberTemplate_L10N"/>
                                                            </xsl:if>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>
                                            </xsl:for-each>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </xsl:variable>
                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                    </xsl:if>
                    <xsl:if test="exists(n1:conventionalInvoiceInfo/n1:generalLedgerAccountNumbers)">
                        <xsl:variable name="altova:table">
                            <fo:table border-collapse="collapse" border-style="none" table-layout="fixed" width="100%" border="solid 1pt gray">
                                <fo:table-column column-width="62.700mm"/>
                                <fo:table-column column-width="127.300mm"/>
                                <xsl:variable name="altova:CurrContextGrid_14" select="."/>
                                <xsl:variable name="altova:ColumnData"/>
                                <fo:table-body start-indent="0pt">
                                    <xsl:for-each select="n1:conventionalInvoiceInfo">
                                        <xsl:for-each select="n1:generalLedgerAccountNumbers">
                                            <xsl:for-each select="n1:generalLedgerAccountNumber">
                                                <fo:table-row>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:if test="position() &lt; 2">
                                                                <xsl:call-template name="ConventionalInfoGeneralLedgerAccountNumberTemplate_L10N"/>
                                                            </xsl:if>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>
                                            </xsl:for-each>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </xsl:variable>
                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                    </xsl:if>
                    <xsl:if test="exists(n1:conventionalInvoiceInfo/n1:glnNumbersSupplier)">
                        <xsl:variable name="altova:table">
                            <fo:table border-collapse="collapse" border-style="none" table-layout="fixed" width="100%" border="solid 1pt gray">
                                <fo:table-column column-width="62.700mm"/>
                                <fo:table-column column-width="127.300mm"/>
                                <xsl:variable name="altova:CurrContextGrid_15" select="."/>
                                <xsl:variable name="altova:ColumnData"/>
                                <fo:table-body start-indent="0pt">
                                    <xsl:for-each select="n1:conventionalInvoiceInfo">
                                        <xsl:for-each select="n1:glnNumbersSupplier">
                                            <xsl:for-each select="n1:glnNumber">
                                                <fo:table-row>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:if test="position() &lt; 2">
                                                                <xsl:call-template name="ConventionalInfoGlnSupplierNumberTemplate_L10N"/>
                                                            </xsl:if>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>
                                            </xsl:for-each>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </xsl:variable>
                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                    </xsl:if>
                    <xsl:if test="exists(n1:conventionalInvoiceInfo/n1:glnNumbersCustomer)">
                        <xsl:variable name="altova:table">
                            <fo:table border-collapse="collapse" border-style="none" table-layout="fixed" width="100%" border="solid 1pt gray">
                                <fo:table-column column-width="62.700mm"/>
                                <fo:table-column column-width="127.300mm"/>
                                <xsl:variable name="altova:CurrContextGrid_16" select="."/>
                                <xsl:variable name="altova:ColumnData"/>
                                <fo:table-body start-indent="0pt">
                                    <xsl:for-each select="n1:conventionalInvoiceInfo">
                                        <xsl:for-each select="n1:glnNumbersCustomer">
                                            <xsl:for-each select="n1:glnNumber">
                                                <fo:table-row>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:if test="position() &lt; 2">
                                                                <xsl:call-template name="ConventionalInfoGlnCustomerNumberTemplate_L10N"/>
                                                            </xsl:if>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>
                                            </xsl:for-each>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </xsl:variable>
                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                    </xsl:if>
                    <xsl:if test="exists(n1:conventionalInvoiceInfo/n1:materialNumbers)">
                        <xsl:variable name="altova:table">
                            <fo:table border-collapse="collapse" border-style="none" table-layout="fixed" width="100%" border="solid 1pt gray">
                                <fo:table-column column-width="62.700mm"/>
                                <fo:table-column column-width="127.300mm"/>
                                <xsl:variable name="altova:CurrContextGrid_17" select="."/>
                                <xsl:variable name="altova:ColumnData"/>
                                <fo:table-body start-indent="0pt">
                                    <xsl:for-each select="n1:conventionalInvoiceInfo">
                                        <xsl:for-each select="n1:materialNumbers">
                                            <xsl:for-each select="n1:materialNumber">
                                                <fo:table-row>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:if test="position() &lt; 2">
                                                                <xsl:call-template name="ConventionalInfoMaterialNumberTemplate_L10N"/>
                                                            </xsl:if>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>
                                            </xsl:for-each>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </xsl:variable>
                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                    </xsl:if>
                    <xsl:if test="exists(n1:conventionalInvoiceInfo/n1:itemNumbers)">
                        <xsl:variable name="altova:table">
                            <fo:table border-collapse="collapse" border-style="none" table-layout="fixed" width="100%" border="solid 1pt gray">
                                <fo:table-column column-width="62.700mm"/>
                                <fo:table-column column-width="127.300mm"/>
                                <xsl:variable name="altova:CurrContextGrid_18" select="."/>
                                <xsl:variable name="altova:ColumnData"/>
                                <fo:table-body start-indent="0pt">
                                    <xsl:for-each select="n1:conventionalInvoiceInfo">
                                        <xsl:for-each select="n1:itemNumbers">
                                            <xsl:for-each select="n1:itemNumber">
                                                <fo:table-row>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:if test="position() &lt; 2">
                                                                <xsl:call-template name="ConventionalInfoItemNumberTemplate_L10N"/>
                                                            </xsl:if>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>
                                            </xsl:for-each>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </xsl:variable>
                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                    </xsl:if>
                    <xsl:if test="exists(n1:conventionalInvoiceInfo/n1:ekaerIds)">
                        <xsl:variable name="altova:table">
                            <fo:table border-collapse="collapse" border-style="none" table-layout="fixed" width="100%" border="solid 1pt gray">
                                <fo:table-column column-width="62.700mm"/>
                                <fo:table-column column-width="127.300mm"/>
                                <xsl:variable name="altova:CurrContextGrid_19" select="."/>
                                <xsl:variable name="altova:ColumnData"/>
                                <fo:table-body start-indent="0pt">
                                    <xsl:for-each select="n1:conventionalInvoiceInfo">
                                        <xsl:for-each select="n1:ekaerIds">
                                            <xsl:for-each select="n1:ekaerId">
                                                <fo:table-row>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:if test="position() &lt; 2">
                                                                <xsl:call-template name="ConventionalInfoEkaerNumberTemplate_L10N"/>
                                                            </xsl:if>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                    <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                        <fo:block>
                                                            <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                                                        </fo:block>
                                                    </fo:table-cell>
                                                </fo:table-row>
                                            </xsl:for-each>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </xsl:variable>
                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                    </xsl:if>
                </fo:block>
                <xsl:if test="exists(n1:additionalInvoiceData)">
                    <fo:block padding-top="5mm" margin-right="100% - 100%" space-before="0" space-after="0" margin="0pt">
                        <xsl:variable name="altova:table">
                            <fo:table border-collapse="collapse" border-style="none" table-layout="fixed" width="100%" border="solid 1pt gray">
                                <fo:table-column column-width="62.700mm"/>
                                <fo:table-column column-width="127.100mm"/>
                                <xsl:variable name="altova:CurrContextGrid_20" select="."/>
                                <xsl:variable name="altova:ColumnData"/>
                                <fo:table-body start-indent="0pt">
                                    <xsl:for-each select="n1:additionalInvoiceData">
                                        <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                            <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <xsl:for-each select="n1:dataDescription">
                                                        <altova:inline-container-substitute font-weight="bold">
                                                            <xsl:apply-templates/>
                                                        </altova:inline-container-substitute>
                                                    </xsl:for-each>
                                                    <fo:inline>
                                                        <xsl:text>:</xsl:text>
                                                    </fo:inline>
                                                    <altova:line-break/>
                                                    <fo:inline>
                                                        <xsl:text>(</xsl:text>
                                                    </fo:inline>
                                                    <xsl:for-each select="n1:dataName">
                                                        <altova:inline-container-substitute>
                                                            <xsl:apply-templates/>
                                                        </altova:inline-container-substitute>
                                                    </xsl:for-each>
                                                    <fo:inline>
                                                        <xsl:text>)</xsl:text>
                                                    </fo:inline>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <xsl:for-each select="n1:dataValue">
                                                        <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                                                    </xsl:for-each>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </xsl:variable>
                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                    </fo:block>
                </xsl:if>
            </xsl:for-each>
        </xsl:for-each>
        <xsl:if test="exists(n1:invoiceLines)">
            <fo:block padding-top="5mm" margin-right="100% - 100%" space-before="0" space-after="0" margin="0pt">
                <xsl:for-each select="n1:invoiceLines">
                    <xsl:if test="n1:mergedItemIndicator = true()">
                        <fo:block color="red" text-align="left" margin-right="100% - 100%" space-before="0" space-after="0" margin="0pt">
                            <xsl:call-template name="MergedItemTemplate_L10N"/>
                        </fo:block>
                    </xsl:if>
                    <altova:line-break/>
                    <xsl:variable name="altova:table">
                        <fo:table border-collapse="collapse" border-style="none" width="100%" table-layout="fixed" border="solid 1pt gray">
                            <fo:table-column column-width="47.500mm"/>
                            <fo:table-column column-width="47.500mm"/>
                            <fo:table-column column-width="47.500mm"/>
                            <fo:table-column column-width="47.500mm"/>
                            <xsl:variable name="altova:CurrContextGrid_21" select="."/>
                            <xsl:variable name="altova:ColumnData"/>
                            <fo:table-body start-indent="0pt">
                                <xsl:for-each select="n1:line">
                                    <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                        <xsl:variable name="sBackground-color">
                                            <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                            </xsl:call-template>
                                        </xsl:variable>
                                        <xsl:if test="$sBackground-color != ''">
                                            <xsl:attribute name="background-color">
                                                <xsl:value-of select="$sBackground-color"/>
                                            </xsl:attribute>
                                        </xsl:if>
                                        <fo:table-cell border-bottom-color="black" border-bottom-style="solid" border-bottom-width="0.01in" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:call-template name="LineNumberTemplate_L10N"/>
                                                <xsl:for-each select="n1:lineNumber">
                                                    <altova:inline-container-substitute font-weight="bold">
                                                        <xsl:apply-templates/>
                                                    </altova:inline-container-substitute>
                                                </xsl:for-each>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell number-columns-spanned="2" border-bottom-color="black" border-bottom-style="solid" border-bottom-width="0.01in" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:choose>
                                                    <xsl:when test="n1:lineModificationReference/n1:lineOperation = &apos;CREATE&apos;">
                                                        <xsl:call-template name="LineOperationCreateTemplate_L10N"/>
                                                    </xsl:when>
                                                    <xsl:when test="n1:lineModificationReference/n1:lineOperation = &apos;MODIFY&apos;">
                                                        <xsl:call-template name="LineOperationModifyTemplate_L10N"/>
                                                    </xsl:when>
                                                </xsl:choose>
                                                <xsl:for-each select="n1:lineModificationReference">
                                                    <xsl:for-each select="n1:lineNumberReference">
                                                        <altova:inline-container-substitute font-weight="bold">
                                                            <xsl:apply-templates/>
                                                        </altova:inline-container-substitute>
                                                    </xsl:for-each>
                                                </xsl:for-each>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-bottom-color="black" border-bottom-style="solid" border-bottom-width="0.01in" border-left-style="none" border-right-style="none" border-top-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:if test="exists(n1:referencesToOtherLines)">
                                                    <xsl:call-template name="ReferenceToOtherLineTemplate_L10N"/>
                                                    <xsl:for-each select="n1:referencesToOtherLines">
                                                        <altova:inline-container-substitute font-weight="bold">
                                                            <xsl:value-of select="string-join(n1:referenceToOtherLine/text(), &apos;, &apos;)"/>
                                                        </altova:inline-container-substitute>
                                                    </xsl:for-each>
                                                </xsl:if>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <xsl:choose>
                                        <xsl:when test="n1:advanceData/n1:advanceIndicator = true()">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="AdvanceIndicatorTemplate_L10N"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                        <xsl:variable name="sBackground-color">
                                            <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                            </xsl:call-template>
                                        </xsl:variable>
                                        <xsl:if test="$sBackground-color != ''">
                                            <xsl:attribute name="background-color">
                                                <xsl:value-of select="$sBackground-color"/>
                                            </xsl:attribute>
                                        </xsl:if>
                                        <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:call-template name="LineDescriptionTemplate_L10N"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell number-columns-spanned="3" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:for-each select="n1:lineDescription">
                                                    <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                                                </xsl:for-each>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                        <xsl:variable name="sBackground-color">
                                            <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                            </xsl:call-template>
                                        </xsl:variable>
                                        <xsl:if test="$sBackground-color != ''">
                                            <xsl:attribute name="background-color">
                                                <xsl:value-of select="$sBackground-color"/>
                                            </xsl:attribute>
                                        </xsl:if>
                                        <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:if test="exists(n1:quantity)">
                                                    <xsl:call-template name="QuantityTemplate_L10N"/>
                                                    <altova:line-break/>
                                                    <xsl:for-each select="n1:quantity">
                                                        <altova:inline-container-substitute font-weight="bold">
                                                            <fo:inline>
                                                                <xsl:value-of select="format-number(number(string(.)), '### ### ### ###', 'format1')"/>
                                                            </fo:inline>
                                                        </altova:inline-container-substitute>
                                                    </xsl:for-each>
                                                </xsl:if>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:if test="exists(n1:unitOfMeasure) or exists(n1:unitOfMeasureOwn)">
                                                    <xsl:call-template name="UnitOfMeasureTemplate_L10N"/>
                                                    <altova:line-break/>
                                                    <xsl:choose>
                                                        <xsl:when test="not(exists(n1:unitOfMeasureOwn)) and exists(n1:unitOfMeasure)">
                                                            <xsl:for-each select="n1:unitOfMeasure">
                                                                <xsl:call-template name="UnitOfMeasureValueTemplate_L10N"/>
                                                            </xsl:for-each>
                                                        </xsl:when>
                                                        <xsl:when test="exists(n1:unitOfMeasureOwn)">
                                                            <xsl:for-each select="n1:unitOfMeasureOwn">
                                                                <altova:inline-container-substitute font-weight="bold">
                                                                    <xsl:apply-templates/>
                                                                </altova:inline-container-substitute>
                                                            </xsl:for-each>
                                                        </xsl:when>
                                                    </xsl:choose>
                                                </xsl:if>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:if test="exists(n1:aggregateInvoiceLineData/n1:lineDeliveryDate)">
                                                    <xsl:call-template name="LineDeliveryDateTemplate_L10N"/>
                                                    <altova:line-break/>
                                                    <xsl:for-each select="n1:aggregateInvoiceLineData">
                                                        <xsl:for-each select="n1:lineDeliveryDate">
                                                            <xsl:call-template name="GenericDateOffset_Inner"/>
                                                        </xsl:for-each>
                                                    </xsl:for-each>
                                                </xsl:if>
                                                <altova:line-break/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:if test="exists(n1:aggregateInvoiceLineData/n1:lineExchangeRate)">
                                                    <xsl:call-template name="LineExchangeRateTemplate_L10N"/>
                                                    <altova:line-break/>
                                                    <xsl:for-each select="n1:aggregateInvoiceLineData">
                                                        <xsl:for-each select="n1:lineExchangeRate">
                                                            <altova:inline-container-substitute font-weight="bold">
                                                                <xsl:apply-templates/>
                                                            </altova:inline-container-substitute>
                                                        </xsl:for-each>
                                                    </xsl:for-each>
                                                </xsl:if>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                        <xsl:variable name="sBackground-color">
                                            <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                            </xsl:call-template>
                                        </xsl:variable>
                                        <xsl:if test="$sBackground-color != ''">
                                            <xsl:attribute name="background-color">
                                                <xsl:value-of select="$sBackground-color"/>
                                            </xsl:attribute>
                                        </xsl:if>
                                        <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:if test="exists(n1:unitPrice)">
                                                    <xsl:call-template name="UnitPriceTemplate_L10N"/>
                                                    <altova:line-break/>
                                                    <xsl:for-each select="n1:unitPrice">
                                                        <xsl:call-template name="GenericQuantityTypeOffset"/>
                                                    </xsl:for-each>
                                                    <fo:inline>
                                                        <xsl:text>&#160;</xsl:text>
                                                    </fo:inline>
                                                    <altova:inline-container-substitute font-weight="bold">
                                                        <xsl:value-of select="$CurrencyCodeValue"/>
                                                    </altova:inline-container-substitute>
                                                </xsl:if>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:if test="exists(n1:lineAmountsNormal/n1:lineNetAmountData/n1:lineNetAmount)">
                                                    <xsl:call-template name="NetAmountTemplate_L10N"/>
                                                    <altova:line-break/>
                                                    <xsl:for-each select="n1:lineAmountsNormal">
                                                        <xsl:for-each select="n1:lineNetAmountData">
                                                            <xsl:for-each select="n1:lineNetAmount">
                                                                <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                    </xsl:for-each>
                                                    <fo:inline>
                                                        <xsl:text>&#160;</xsl:text>
                                                    </fo:inline>
                                                    <altova:inline-container-substitute font-weight="bold">
                                                        <xsl:value-of select="$CurrencyCodeValue"/>
                                                    </altova:inline-container-substitute>
                                                </xsl:if>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:if test="exists(n1:lineAmountsNormal/n1:lineVatData/n1:lineVatAmount)">
                                                    <xsl:call-template name="VatAmountTemplate_L10N"/>
                                                    <altova:line-break/>
                                                    <xsl:for-each select="n1:lineAmountsNormal">
                                                        <xsl:for-each select="n1:lineVatData">
                                                            <xsl:for-each select="n1:lineVatAmount">
                                                                <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                    </xsl:for-each>
                                                    <fo:inline>
                                                        <xsl:text>&#160;</xsl:text>
                                                    </fo:inline>
                                                    <altova:inline-container-substitute font-weight="bold">
                                                        <xsl:value-of select="$CurrencyCodeValue"/>
                                                    </altova:inline-container-substitute>
                                                </xsl:if>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:choose>
                                                    <xsl:when test="exists(n1:lineAmountsNormal/n1:lineGrossAmountData/n1:lineGrossAmountNormal)">
                                                        <xsl:call-template name="GrossAmountTemplate_L10N"/>
                                                        <altova:line-break/>
                                                        <xsl:for-each select="n1:lineAmountsNormal">
                                                            <xsl:for-each select="n1:lineGrossAmountData">
                                                                <xsl:for-each select="n1:lineGrossAmountNormal">
                                                                    <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                                </xsl:for-each>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                        <fo:inline>
                                                            <xsl:text>&#160;</xsl:text>
                                                        </fo:inline>
                                                        <altova:inline-container-substitute font-weight="bold">
                                                            <xsl:value-of select="$CurrencyCodeValue"/>
                                                        </altova:inline-container-substitute>
                                                    </xsl:when>
                                                    <xsl:when test="exists(n1:lineAmountsSimplified/n1:lineGrossAmountSimplified)">
                                                        <xsl:call-template name="GrossAmountTemplate_L10N"/>
                                                        <altova:line-break/>
                                                        <xsl:for-each select="n1:lineAmountsSimplified">
                                                            <xsl:for-each select="n1:lineGrossAmountSimplified">
                                                                <altova:inline-container-substitute font-weight="bold">
                                                                    <xsl:apply-templates/>
                                                                </altova:inline-container-substitute>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                        <fo:inline>
                                                            <xsl:text>&#160; </xsl:text>
                                                        </fo:inline>
                                                        <altova:inline-container-substitute font-weight="bold">
                                                            <xsl:value-of select="$CurrencyCodeValue"/>
                                                        </altova:inline-container-substitute>
                                                    </xsl:when>
                                                </xsl:choose>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:lineAmountsNormal)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:for-each select="n1:lineAmountsNormal">
                                                            <xsl:for-each select="n1:lineVatRate">
                                                                <xsl:call-template name="VatRateTypeTemplate_C5X"/>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell number-columns-spanned="3" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:if test="exists(n1:lineAmountsNormal/n1:lineVatRate/n1:vatExemption/n1:reason) or exists(n1:lineAmountsNormal/n1:lineVatRate/n1:vatOutOfScope/n1:reason) or n1:lineAmountsNormal/n1:lineVatRate/n1:vatDomesticReverseCharge = true() or exists(n1:lineAmountsNormal/n1:lineVatRate/n1:marginSchemeIndicator) or exists(n1:lineAmountsNormal/n1:lineVatRate/n1:vatAmountMismatch/n1:case) or n1:lineAmountsNormal/n1:lineVatRate/n1:noVatCharge = true()">
                                                            <xsl:call-template name="VatDescriptionTemplate_L10N"/>
                                                            <xsl:for-each select="n1:lineAmountsNormal">
                                                                <xsl:for-each select="n1:lineVatRate">
                                                                    <xsl:call-template name="VatReasonTemplate_C5X"/>
                                                                </xsl:for-each>
                                                            </xsl:for-each>
                                                        </xsl:if>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:lineAmountsSimplified)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:for-each select="n1:lineAmountsSimplified">
                                                            <xsl:for-each select="n1:lineVatRate">
                                                                <xsl:call-template name="VatRateTypeTemplate_C5X"/>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell number-columns-spanned="3" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:if test="exists(n1:lineAmountsSimplified/n1:lineVatRate/n1:vatExemption/n1:reason) or exists(n1:lineAmountsSimplified/n1:lineVatRate/n1:vatOutOfScope/n1:reason) or n1:lineAmountsSimplified/n1:lineVatRate/n1:vatDomesticReverseCharge = true() or exists(n1:lineAmountsSimplified/n1:lineVatRate/n1:marginSchemeIndicator) or exists(n1:lineAmountsSimplified/n1:lineVatRate/n1:vatAmountMismatch/n1:case) or n1:lineAmountsSimplified/n1:lineVatRate/n1:noVatCharge = true()">
                                                            <xsl:call-template name="VatDescriptionTemplate_L10N"/>
                                                            <xsl:for-each select="n1:lineAmountsSimplified">
                                                                <xsl:for-each select="n1:lineVatRate">
                                                                    <xsl:call-template name="VatReasonTemplate_C5X"/>
                                                                </xsl:for-each>
                                                            </xsl:for-each>
                                                        </xsl:if>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                        <xsl:variable name="sBackground-color">
                                            <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                            </xsl:call-template>
                                        </xsl:variable>
                                        <xsl:if test="$sBackground-color != ''">
                                            <xsl:attribute name="background-color">
                                                <xsl:value-of select="$sBackground-color"/>
                                            </xsl:attribute>
                                        </xsl:if>
                                        <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:call-template name="UnitPriceHUFTemplate_L10N"/>
                                                <altova:line-break/>
                                                <xsl:for-each select="n1:unitPriceHUF">
                                                    <xsl:call-template name="GenericQuantityTypeOffset"/>
                                                </xsl:for-each>
                                                <fo:inline font-weight="bold">
                                                    <xsl:text> HUF</xsl:text>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:if test="exists(n1:lineAmountsNormal/n1:lineNetAmountData/n1:lineNetAmountHUF)">
                                                    <xsl:call-template name="LineNetAmountHUFTemplate_L10N"/>
                                                    <altova:line-break/>
                                                    <xsl:for-each select="n1:lineAmountsNormal">
                                                        <xsl:for-each select="n1:lineNetAmountData">
                                                            <xsl:for-each select="n1:lineNetAmountHUF">
                                                                <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                    </xsl:for-each>
                                                    <fo:inline>
                                                        <xsl:text>&#160;</xsl:text>
                                                    </fo:inline>
                                                    <fo:inline font-weight="bold">
                                                        <xsl:text>HUF</xsl:text>
                                                    </fo:inline>
                                                </xsl:if>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:if test="exists(n1:lineAmountsNormal/n1:lineVatData/n1:lineVatAmountHUF)">
                                                    <xsl:call-template name="LineVatAmountHUFTemplate_L10N"/>
                                                    <altova:line-break/>
                                                    <xsl:for-each select="n1:lineAmountsNormal">
                                                        <xsl:for-each select="n1:lineVatData">
                                                            <xsl:for-each select="n1:lineVatAmountHUF">
                                                                <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                    </xsl:for-each>
                                                    <fo:inline>
                                                        <xsl:text>&#160;</xsl:text>
                                                    </fo:inline>
                                                    <fo:inline font-weight="bold">
                                                        <xsl:text>HUF</xsl:text>
                                                    </fo:inline>
                                                </xsl:if>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:choose>
                                                    <xsl:when test="exists(n1:lineAmountsNormal/n1:lineGrossAmountData/n1:lineGrossAmountNormalHUF)">
                                                        <xsl:call-template name="LineGrossAmountHUFTemplate_L10N"/>
                                                        <altova:line-break/>
                                                        <xsl:for-each select="n1:lineAmountsNormal">
                                                            <xsl:for-each select="n1:lineGrossAmountData">
                                                                <xsl:for-each select="n1:lineGrossAmountNormalHUF">
                                                                    <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                                </xsl:for-each>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                        <fo:inline>
                                                            <xsl:text>&#160;</xsl:text>
                                                        </fo:inline>
                                                        <fo:inline font-weight="bold">
                                                            <xsl:text>HUF</xsl:text>
                                                        </fo:inline>
                                                    </xsl:when>
                                                    <xsl:when test="exists(n1:lineAmountsSimplified/n1:lineGrossAmountSimplified)">
                                                        <xsl:call-template name="LineGrossAmountHUFTemplate_L10N"/>
                                                        <altova:line-break/>
                                                        <xsl:for-each select="n1:lineAmountsSimplified">
                                                            <xsl:for-each select="n1:lineGrossAmountSimplifiedHUF">
                                                                <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                    </xsl:when>
                                                </xsl:choose>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:lineDiscountData)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="LineDiscountDataTemplate_L10N"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="LineDiscountDescriptionTemplate_L10N"/>
                                                        <altova:line-break/>
                                                        <xsl:for-each select="n1:lineDiscountData">
                                                            <xsl:for-each select="n1:discountDescription">
                                                                <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="LineDiscountValueTemplate_L10N"/>
                                                        <altova:line-break/>
                                                        <xsl:for-each select="n1:lineDiscountData">
                                                            <xsl:for-each select="n1:discountValue">
                                                                <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                        <fo:inline>
                                                            <xsl:text>&#160;</xsl:text>
                                                        </fo:inline>
                                                        <altova:inline-container-substitute font-weight="bold">
                                                            <xsl:value-of select="$CurrencyCodeValue"/>
                                                        </altova:inline-container-substitute>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="LineDiscountRateTemplate_L10N"/>
                                                        <altova:line-break/>
                                                        <xsl:for-each select="n1:lineDiscountData">
                                                            <xsl:for-each select="n1:discountRate">
                                                                <altova:inline-container-substitute font-weight="bold">
                                                                    <xsl:value-of select=". * 100"/>
                                                                </altova:inline-container-substitute>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                        <fo:inline font-weight="bold">
                                                            <xsl:text> %</xsl:text>
                                                        </fo:inline>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:advanceData/n1:advancePaymentData)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="AdvancePaymentTemplate_L10N"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:if test="exists(n1:advanceData/n1:advancePaymentData)">
                                                            <xsl:call-template name="AdvanceOriginalInvoiceTemplate_L10N"/>
                                                            <altova:line-break/>
                                                            <xsl:for-each select="n1:advanceData">
                                                                <xsl:for-each select="n1:advancePaymentData">
                                                                    <xsl:for-each select="n1:advanceOriginalInvoice">
                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                            <xsl:apply-templates/>
                                                                        </altova:inline-container-substitute>
                                                                    </xsl:for-each>
                                                                </xsl:for-each>
                                                            </xsl:for-each>
                                                        </xsl:if>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:if test="exists(n1:advanceData/n1:advancePaymentData)">
                                                            <xsl:call-template name="AdvancePaymentDateTemplate_L10N"/>
                                                            <altova:line-break/>
                                                            <xsl:for-each select="n1:advanceData">
                                                                <xsl:for-each select="n1:advancePaymentData">
                                                                    <xsl:for-each select="n1:advancePaymentDate">
                                                                        <xsl:call-template name="GenericDateOffset_Inner"/>
                                                                    </xsl:for-each>
                                                                </xsl:for-each>
                                                            </xsl:for-each>
                                                        </xsl:if>
                                                        <altova:line-break/>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:if test="exists(n1:advanceData/n1:advancePaymentData)">
                                                            <xsl:call-template name="AdvanceExchangeRateTemplate_L10N"/>
                                                            <altova:line-break/>
                                                            <xsl:for-each select="n1:advanceData">
                                                                <xsl:for-each select="n1:advancePaymentData">
                                                                    <xsl:for-each select="n1:advanceExchangeRate">
                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                            <xsl:apply-templates/>
                                                                        </altova:inline-container-substitute>
                                                                    </xsl:for-each>
                                                                </xsl:for-each>
                                                            </xsl:for-each>
                                                        </xsl:if>
                                                        <altova:line-break/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:productCodes)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="ProductCodesData_L10N"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell number-columns-spanned="3" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:for-each select="n1:productCodes">
                                                            <altova:inline-container-substitute font-weight="bold">
                                                                <xsl:value-of select="string-join(n1:productCode/string-join(*, &apos; - &apos;), &apos;, &apos;)"/>
                                                            </altova:inline-container-substitute>
                                                        </xsl:for-each>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="n1:intermediatedService = true()">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="IntermediatedServiceTemplate_L10N"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="n1:depositIndicator = true()">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="DepositIndicatorTemplate_L10N"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="n1:obligatedForProductFee = true()">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="2" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="ObligatedForProductFeeTemplate_L10N"/>
                                                        <fo:inline>
                                                            <xsl:text>&#160;</xsl:text>
                                                        </fo:inline>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell number-columns-spanned="2" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:choose>
                                                            <xsl:when test="exists(n1:productFeeClause/n1:productFeeTakeoverData)">
                                                                <xsl:call-template name="ProductFeeTakeoverTemplate_L10N"/>
                                                            </xsl:when>
                                                            <xsl:when test="exists(n1:productFeeClause/n1:customerDeclaration)">
                                                                <xsl:call-template name="ProductFeeCustomerDeclarationTemplate_L10N"/>
                                                            </xsl:when>
                                                        </xsl:choose>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:productFeeClause)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="2" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:choose>
                                                            <xsl:when test="exists(n1:productFeeClause/n1:productFeeTakeoverData)">
                                                                <xsl:call-template name="TakeoverTemplate_L10N"/>
                                                                <altova:line-break/>
                                                                <xsl:for-each select="n1:productFeeClause">
                                                                    <xsl:for-each select="n1:productFeeTakeoverData">
                                                                        <xsl:for-each select="n1:takeoverReason">
                                                                            <altova:inline-container-substitute font-weight="bold">
                                                                                <xsl:apply-templates/>
                                                                            </altova:inline-container-substitute>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </xsl:for-each>
                                                            </xsl:when>
                                                            <xsl:when test="exists(n1:productFeeClause/n1:customerDeclaration)">
                                                                <xsl:call-template name="ProductStreamTemplate_L10N"/>
                                                                <altova:line-break/>
                                                                <xsl:for-each select="n1:productFeeClause">
                                                                    <xsl:for-each select="n1:customerDeclaration">
                                                                        <xsl:call-template name="ProductStreamTemplate_C5X"/>
                                                                    </xsl:for-each>
                                                                </xsl:for-each>
                                                            </xsl:when>
                                                        </xsl:choose>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell number-columns-spanned="2" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:choose>
                                                            <xsl:when test="exists(n1:productFeeClause/n1:productFeeTakeoverData)">
                                                                <xsl:call-template name="TakeoverAmountTemplate_L10N"/>
                                                                <altova:line-break/>
                                                                <xsl:for-each select="n1:productFeeClause">
                                                                    <xsl:for-each select="n1:productFeeTakeoverData">
                                                                        <xsl:for-each select="n1:takeoverAmount">
                                                                            <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </xsl:for-each>
                                                                <fo:inline>
                                                                    <xsl:text>&#160;</xsl:text>
                                                                </fo:inline>
                                                                <fo:inline font-weight="bold">
                                                                    <xsl:text>HUF</xsl:text>
                                                                </fo:inline>
                                                            </xsl:when>
                                                            <xsl:when test="exists(n1:productFeeClause/n1:customerDeclaration)">
                                                                <xsl:call-template name="ProductFeeWeightTemplate_L10N"/>
                                                                <altova:line-break/>
                                                                <xsl:for-each select="n1:productFeeClause">
                                                                    <xsl:for-each select="n1:customerDeclaration">
                                                                        <xsl:for-each select="n1:productFeeWeight">
                                                                            <xsl:call-template name="GenericQuantityTypeOffset"/>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </xsl:for-each>
                                                                <fo:inline>
                                                                    <xsl:text>&#160;</xsl:text>
                                                                </fo:inline>
                                                                <fo:inline font-weight="bold">
                                                                    <xsl:text>kg</xsl:text>
                                                                </fo:inline>
                                                            </xsl:when>
                                                        </xsl:choose>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:lineProductFeeContent)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:variable name="altova:table">
                                                            <fo:table border-collapse="collapse" border-style="none" table-layout="fixed" width="100%">
                                                                <fo:table-column column-width="100%"/>
                                                                <xsl:variable name="altova:CurrContextGrid_22" select="."/>
                                                                <xsl:variable name="altova:ColumnData"/>
                                                                <fo:table-body start-indent="0pt">
                                                                    <xsl:for-each select="n1:lineProductFeeContent">
                                                                        <fo:table-row>
                                                                            <fo:table-cell border-style="none" padding="2pt" display-align="center">
                                                                                <fo:block>
                                                                                    <altova:inline-container-substitute font-weight="bold">
                                                                                        <xsl:value-of select="string-join(./n1:productFeeCode/*, &apos; - &apos;)"/>
                                                                                    </altova:inline-container-substitute>
                                                                                    <fo:inline>
                                                                                        <xsl:text>&#160;</xsl:text>
                                                                                    </fo:inline>
                                                                                    <xsl:call-template name="ProductFeeCodeAmount_L10N"/>
                                                                                    <xsl:for-each select="n1:productFeeAmount">
                                                                                        <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                                                    </xsl:for-each>
                                                                                    <fo:inline>
                                                                                        <xsl:text>&#160;</xsl:text>
                                                                                    </fo:inline>
                                                                                    <fo:inline font-weight="bold">
                                                                                        <xsl:text>HUF</xsl:text>
                                                                                    </fo:inline>
                                                                                    <fo:inline>
                                                                                        <xsl:text>, </xsl:text>
                                                                                    </fo:inline>
                                                                                    <xsl:call-template name="ProductFeeQuantityTemplate_L10N"/>
                                                                                    <xsl:for-each select="n1:productFeeQuantity">
                                                                                        <xsl:call-template name="GenericQuantityTypeOffset"/>
                                                                                    </xsl:for-each>
                                                                                    <fo:inline>
                                                                                        <xsl:text>&#160;</xsl:text>
                                                                                    </fo:inline>
                                                                                    <xsl:for-each select="n1:productFeeMeasuringUnit">
                                                                                        <xsl:call-template name="ProductFeeMeasuringUnitTemplate_L10N"/>
                                                                                    </xsl:for-each>
                                                                                    <fo:inline>
                                                                                        <xsl:text>, </xsl:text>
                                                                                    </fo:inline>
                                                                                    <xsl:call-template name="ProductFeeRateTemplate_L10N"/>
                                                                                    <xsl:for-each select="n1:productFeeRate">
                                                                                        <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                                                    </xsl:for-each>
                                                                                    <fo:inline>
                                                                                        <xsl:text>&#160;</xsl:text>
                                                                                    </fo:inline>
                                                                                    <fo:inline font-weight="bold">
                                                                                        <xsl:text>HUF / </xsl:text>
                                                                                    </fo:inline>
                                                                                    <xsl:for-each select="n1:productFeeMeasuringUnit">
                                                                                        <xsl:call-template name="ProductFeeMeasuringUnitTemplate_L10N"/>
                                                                                    </xsl:for-each>
                                                                                </fo:block>
                                                                            </fo:table-cell>
                                                                        </fo:table-row>
                                                                    </xsl:for-each>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </xsl:variable>
                                                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:GPCExcise)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="GPCExciseTemplate_L10N"/>
                                                        <xsl:for-each select="n1:GPCExcise">
                                                            <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                        </xsl:for-each>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="n1:netaDeclaration = true()">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="NetaDeclarationTemplate_L10N"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:newTransportMean)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="NewTransportMeanTemplate_L10N"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:choose>
                                                            <xsl:when test="exists(n1:newTransportMean/n1:vehicle)">
                                                                <xsl:call-template name="EngineCapacityTemplate_L10N"/>
                                                                <altova:line-break/>
                                                                <xsl:for-each select="n1:newTransportMean">
                                                                    <xsl:for-each select="n1:vehicle">
                                                                        <xsl:for-each select="n1:engineCapacity">
                                                                            <xsl:call-template name="GenericQuantityTypeOffset"/>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </xsl:for-each>
                                                                <fo:inline font-weight="bold">
                                                                    <xsl:text> cm3</xsl:text>
                                                                </fo:inline>
                                                            </xsl:when>
                                                            <xsl:when test="exists(n1:newTransportMean/n1:vessel)">
                                                                <xsl:call-template name="LengthTemplate_L10N"/>
                                                                <altova:line-break/>
                                                                <xsl:for-each select="n1:newTransportMean">
                                                                    <xsl:for-each select="n1:vessel">
                                                                        <xsl:for-each select="n1:length">
                                                                            <xsl:call-template name="GenericQuantityTypeOffset"/>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </xsl:for-each>
                                                                <fo:inline font-weight="bold">
                                                                    <xsl:text> m</xsl:text>
                                                                </fo:inline>
                                                            </xsl:when>
                                                            <xsl:when test="exists(n1:newTransportMean/n1:aircraft)">
                                                                <xsl:call-template name="TakeOffWeightTemplate_L10N"/>
                                                                <altova:line-break/>
                                                                <xsl:for-each select="n1:newTransportMean">
                                                                    <xsl:for-each select="n1:aircraft">
                                                                        <xsl:for-each select="n1:takeOffWeight">
                                                                            <xsl:call-template name="GenericQuantityTypeOffset"/>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </xsl:for-each>
                                                                <fo:inline font-weight="bold">
                                                                    <xsl:text> kg</xsl:text>
                                                                </fo:inline>
                                                            </xsl:when>
                                                        </xsl:choose>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:choose>
                                                            <xsl:when test="exists(n1:newTransportMean/n1:vehicle)">
                                                                <xsl:call-template name="EnginePowerTemplate_L10N"/>
                                                                <altova:line-break/>
                                                                <xsl:for-each select="n1:newTransportMean">
                                                                    <xsl:for-each select="n1:vehicle">
                                                                        <xsl:for-each select="n1:enginePower">
                                                                            <xsl:call-template name="GenericQuantityTypeOffset"/>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </xsl:for-each>
                                                                <fo:inline font-weight="bold">
                                                                    <xsl:text> kw</xsl:text>
                                                                </fo:inline>
                                                            </xsl:when>
                                                            <xsl:when test="n1:newTransportMean/n1:vessel/n1:activityReferred = true() or n1:newTransportMean/n1:aircraft/n1:airCargo = true()">
                                                                <xsl:call-template name="NewTransportException"/>
                                                            </xsl:when>
                                                        </xsl:choose>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:choose>
                                                            <xsl:when test="exists(n1:newTransportMean/n1:vehicle)">
                                                                <xsl:call-template name="KmsTemplate_L10N"/>
                                                                <altova:line-break/>
                                                                <xsl:for-each select="n1:newTransportMean">
                                                                    <xsl:for-each select="n1:vehicle">
                                                                        <xsl:for-each select="n1:kms">
                                                                            <xsl:call-template name="GenericQuantityTypeOffset"/>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </xsl:for-each>
                                                                <fo:inline font-weight="bold">
                                                                    <xsl:text> km</xsl:text>
                                                                </fo:inline>
                                                            </xsl:when>
                                                            <xsl:when test="exists(n1:newTransportMean/n1:vessel)">
                                                                <xsl:call-template name="SailedHoursTemplate_L10N"/>
                                                                <altova:line-break/>
                                                                <xsl:for-each select="n1:newTransportMean">
                                                                    <xsl:for-each select="n1:vessel">
                                                                        <xsl:for-each select="n1:sailedHours">
                                                                            <xsl:call-template name="GenericQuantityTypeOffset"/>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </xsl:for-each>
                                                                <fo:inline font-weight="bold">
                                                                    <xsl:text> h</xsl:text>
                                                                </fo:inline>
                                                            </xsl:when>
                                                            <xsl:when test="exists(n1:newTransportMean/n1:aircraft)">
                                                                <xsl:call-template name="OperationHoursTemplate_L10N"/>
                                                                <altova:line-break/>
                                                                <xsl:for-each select="n1:newTransportMean">
                                                                    <xsl:for-each select="n1:aircraft">
                                                                        <xsl:for-each select="n1:operationHours">
                                                                            <xsl:call-template name="GenericQuantityTypeOffset"/>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </xsl:for-each>
                                                                <fo:inline font-weight="bold">
                                                                    <xsl:text> h</xsl:text>
                                                                </fo:inline>
                                                            </xsl:when>
                                                        </xsl:choose>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="count(child::n1:newTransportMean) &gt; 0">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="BrandTemplate_L10N"/>
                                                        <altova:line-break/>
                                                        <xsl:for-each select="n1:newTransportMean">
                                                            <xsl:for-each select="n1:brand">
                                                                <altova:inline-container-substitute font-weight="bold">
                                                                    <xsl:apply-templates/>
                                                                </altova:inline-container-substitute>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="SerialNumTemplate_L10N"/>
                                                        <altova:line-break/>
                                                        <xsl:for-each select="n1:newTransportMean">
                                                            <xsl:for-each select="n1:serialNum">
                                                                <altova:inline-container-substitute font-weight="bold">
                                                                    <xsl:apply-templates/>
                                                                </altova:inline-container-substitute>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="EngineNumTemplate_L10N"/>
                                                        <altova:line-break/>
                                                        <xsl:for-each select="n1:newTransportMean">
                                                            <xsl:for-each select="n1:engineNum">
                                                                <altova:inline-container-substitute font-weight="bold">
                                                                    <xsl:apply-templates/>
                                                                </altova:inline-container-substitute>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="FirstEntryIntoServiceTemplate_L10N"/>
                                                        <altova:line-break/>
                                                        <xsl:for-each select="n1:newTransportMean">
                                                            <xsl:for-each select="n1:firstEntryIntoService">
                                                                <xsl:call-template name="GenericDateOffset_Inner"/>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:dieselOilPurchase)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="DieselOilPurchaseTemplate_L10N"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="DieselOilPurchaseDateTemplate_L10N"/>
                                                        <altova:line-break/>
                                                        <xsl:for-each select="n1:dieselOilPurchase">
                                                            <xsl:for-each select="n1:purchaseDate">
                                                                <xsl:call-template name="GenericDateOffset_Inner"/>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="DieselOilPurchaseVehicleRegistrationNumberTemplate_L10N"/>
                                                        <altova:line-break/>
                                                        <xsl:for-each select="n1:dieselOilPurchase">
                                                            <xsl:for-each select="n1:vehicleRegistrationNumber">
                                                                <altova:inline-container-substitute font-weight="bold">
                                                                    <xsl:apply-templates/>
                                                                </altova:inline-container-substitute>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:if test="exists(n1:dieselOilPurchase/n1:dieselOilQuantity)">
                                                            <xsl:call-template name="DieselOilQuantityTemplate_L10N"/>
                                                            <altova:line-break/>
                                                            <xsl:for-each select="n1:dieselOilPurchase">
                                                                <xsl:for-each select="n1:dieselOilQuantity">
                                                                    <xsl:call-template name="GenericQuantityTypeOffset"/>
                                                                </xsl:for-each>
                                                            </xsl:for-each>
                                                            <fo:inline>
                                                                <xsl:text>&#160;</xsl:text>
                                                            </fo:inline>
                                                            <fo:inline font-weight="bold">
                                                                <xsl:text>l</xsl:text>
                                                            </fo:inline>
                                                        </xsl:if>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:dieselOilPurchase)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="DieselOilPurchaseLocationTemplate_L10N"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell number-columns-spanned="3" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:for-each select="n1:dieselOilPurchase">
                                                            <xsl:for-each select="n1:purchaseLocation">
                                                                <xsl:for-each select="base:countryCode">
                                                                    <altova:inline-container-substitute font-weight="bold">
                                                                        <xsl:apply-templates/>
                                                                    </altova:inline-container-substitute>
                                                                </xsl:for-each>
                                                                <fo:inline>
                                                                    <xsl:text>&#160;</xsl:text>
                                                                </fo:inline>
                                                                <xsl:if test="exists(n1:dieselOilPurchase/n1:purchaseLocation/base:region)">
                                                                    <xsl:for-each select="base:region">
                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                            <xsl:apply-templates/>
                                                                        </altova:inline-container-substitute>
                                                                    </xsl:for-each>
                                                                    <fo:inline>
                                                                        <xsl:text>&#160;</xsl:text>
                                                                    </fo:inline>
                                                                </xsl:if>
                                                                <xsl:for-each select="base:postalCode">
                                                                    <altova:inline-container-substitute font-weight="bold">
                                                                        <xsl:apply-templates/>
                                                                    </altova:inline-container-substitute>
                                                                </xsl:for-each>
                                                                <fo:inline>
                                                                    <xsl:text>, </xsl:text>
                                                                </fo:inline>
                                                                <xsl:for-each select="base:city">
                                                                    <altova:inline-container-substitute font-weight="bold">
                                                                        <xsl:apply-templates/>
                                                                    </altova:inline-container-substitute>
                                                                </xsl:for-each>
                                                                <fo:inline>
                                                                    <xsl:text>&#160;</xsl:text>
                                                                </fo:inline>
                                                                <xsl:for-each select="base:additionalAddressDetail">
                                                                    <altova:inline-container-substitute font-weight="bold">
                                                                        <xsl:apply-templates/>
                                                                    </altova:inline-container-substitute>
                                                                </xsl:for-each>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:conventionalLineInfo/n1:orderNumbers)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:variable name="altova:table">
                                                            <fo:table border-collapse="collapse" border-style="none" width="100%" table-layout="fixed">
                                                                <fo:table-column column-width="25%"/>
                                                                <fo:table-column column-width="75%"/>
                                                                <xsl:variable name="altova:CurrContextGrid_23" select="."/>
                                                                <xsl:variable name="altova:ColumnData"/>
                                                                <fo:table-body start-indent="0pt">
                                                                    <xsl:for-each select="n1:conventionalLineInfo">
                                                                        <xsl:for-each select="n1:orderNumbers">
                                                                            <xsl:variable name="conventionalLine" select="string-join(n1:orderNumber/text(), &apos;, &apos;)"/>
                                                                            <fo:table-row>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <xsl:call-template name="ConventionalInfoOrderNumberTemplate_L10N"/>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                                            <xsl:value-of select="string-join(for $i in (string-to-codepoints($conventionalLine)) return codepoints-to-string($i), codepoints-to-string(8203))"/>
                                                                                        </altova:inline-container-substitute>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                            </fo:table-row>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </xsl:variable>
                                                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:conventionalLineInfo/n1:deliveryNotes)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:variable name="altova:table">
                                                            <fo:table border-collapse="collapse" border-style="none" width="100%" table-layout="fixed">
                                                                <fo:table-column column-width="25%"/>
                                                                <fo:table-column column-width="75%"/>
                                                                <xsl:variable name="altova:CurrContextGrid_24" select="."/>
                                                                <xsl:variable name="altova:ColumnData"/>
                                                                <fo:table-body start-indent="0pt">
                                                                    <xsl:for-each select="n1:conventionalLineInfo">
                                                                        <xsl:for-each select="n1:deliveryNotes">
                                                                            <xsl:variable name="conventionalLine" select="string-join(n1:deliveryNote/text(), &apos;, &apos;)"/>
                                                                            <fo:table-row>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <xsl:call-template name="ConventionalInfoDeliveryNoteTemplate_L10N"/>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                                            <xsl:value-of select="string-join(for $i in (string-to-codepoints($conventionalLine)) return codepoints-to-string($i), codepoints-to-string(8203))"/>
                                                                                        </altova:inline-container-substitute>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                            </fo:table-row>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </xsl:variable>
                                                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:conventionalLineInfo/n1:shippingDates)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:variable name="altova:table">
                                                            <fo:table border-collapse="collapse" border-style="none" width="100%" table-layout="fixed">
                                                                <fo:table-column column-width="25%"/>
                                                                <fo:table-column column-width="75%"/>
                                                                <xsl:variable name="altova:CurrContextGrid_25" select="."/>
                                                                <xsl:variable name="altova:ColumnData"/>
                                                                <fo:table-body start-indent="0pt">
                                                                    <xsl:for-each select="n1:conventionalLineInfo">
                                                                        <xsl:for-each select="n1:shippingDates">
                                                                            <xsl:variable name="conventionalLine" select="string-join(n1:shippingDate/text(), &apos;, &apos;)"/>
                                                                            <fo:table-row>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <xsl:call-template name="ConventionalInfoShippingDateTemplate_L10N"/>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                                            <xsl:value-of select="string-join(for $i in (string-to-codepoints($conventionalLine)) return codepoints-to-string($i), codepoints-to-string(8203))"/>
                                                                                        </altova:inline-container-substitute>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                            </fo:table-row>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </xsl:variable>
                                                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:conventionalLineInfo/n1:contractNumbers)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:variable name="altova:table">
                                                            <fo:table border-collapse="collapse" border-style="none" width="100%" table-layout="fixed">
                                                                <fo:table-column column-width="25%"/>
                                                                <fo:table-column column-width="75%"/>
                                                                <xsl:variable name="altova:CurrContextGrid_26" select="."/>
                                                                <xsl:variable name="altova:ColumnData"/>
                                                                <fo:table-body start-indent="0pt">
                                                                    <xsl:for-each select="n1:conventionalLineInfo">
                                                                        <xsl:for-each select="n1:contractNumbers">
                                                                            <xsl:variable name="conventionalLine" select="string-join(n1:contractNumber/text(), &apos;, &apos;)"/>
                                                                            <fo:table-row>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <xsl:call-template name="ConventionalInfoContractNumberTemplate_L10N"/>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                                            <xsl:value-of select="string-join(for $i in (string-to-codepoints($conventionalLine)) return codepoints-to-string($i), codepoints-to-string(8203))"/>
                                                                                        </altova:inline-container-substitute>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                            </fo:table-row>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </xsl:variable>
                                                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:conventionalLineInfo/n1:supplierCompanyCodes)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:variable name="altova:table">
                                                            <fo:table border-collapse="collapse" border-style="none" width="100%" table-layout="fixed">
                                                                <fo:table-column column-width="25%"/>
                                                                <fo:table-column column-width="75%"/>
                                                                <xsl:variable name="altova:CurrContextGrid_27" select="."/>
                                                                <xsl:variable name="altova:ColumnData"/>
                                                                <fo:table-body start-indent="0pt">
                                                                    <xsl:for-each select="n1:conventionalLineInfo">
                                                                        <xsl:for-each select="n1:supplierCompanyCodes">
                                                                            <xsl:variable name="conventionalLine" select="string-join(n1:supplierCompanyCode/text(), &apos;, &apos;)"/>
                                                                            <fo:table-row>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <xsl:call-template name="ConventionalInfoSupplierCompanyCodeTemplate_L10N"/>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                                            <xsl:value-of select="string-join(for $i in (string-to-codepoints($conventionalLine)) return codepoints-to-string($i), codepoints-to-string(8203))"/>
                                                                                        </altova:inline-container-substitute>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                            </fo:table-row>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </xsl:variable>
                                                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:conventionalLineInfo/n1:customerCompanyCodes)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:variable name="altova:table">
                                                            <fo:table border-collapse="collapse" border-style="none" width="100%" table-layout="fixed">
                                                                <fo:table-column column-width="25%"/>
                                                                <fo:table-column column-width="75%"/>
                                                                <xsl:variable name="altova:CurrContextGrid_28" select="."/>
                                                                <xsl:variable name="altova:ColumnData"/>
                                                                <fo:table-body start-indent="0pt">
                                                                    <xsl:for-each select="n1:conventionalLineInfo">
                                                                        <xsl:for-each select="n1:customerCompanyCodes">
                                                                            <xsl:variable name="conventionalLine" select="string-join(n1:customerCompanyCode/text(), &apos;, &apos;)"/>
                                                                            <fo:table-row>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <xsl:call-template name="ConventionalInfoCustomerCompanyCodeTemplate_L10N"/>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                                            <xsl:value-of select="string-join(for $i in (string-to-codepoints($conventionalLine)) return codepoints-to-string($i), codepoints-to-string(8203))"/>
                                                                                        </altova:inline-container-substitute>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                            </fo:table-row>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </xsl:variable>
                                                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:conventionalLineInfo/n1:dealerCodes)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:variable name="altova:table">
                                                            <fo:table border-collapse="collapse" border-style="none" width="100%" table-layout="fixed">
                                                                <fo:table-column column-width="25%"/>
                                                                <fo:table-column column-width="75%"/>
                                                                <xsl:variable name="altova:CurrContextGrid_29" select="."/>
                                                                <xsl:variable name="altova:ColumnData"/>
                                                                <fo:table-body start-indent="0pt">
                                                                    <xsl:for-each select="n1:conventionalLineInfo">
                                                                        <xsl:for-each select="n1:dealerCodes">
                                                                            <xsl:variable name="conventionalLine" select="string-join(n1:dealerCode/text(), &apos;, &apos;)"/>
                                                                            <fo:table-row>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <xsl:call-template name="ConventionalInfoDealerCodeTemplate_L10N"/>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                                            <xsl:value-of select="string-join(for $i in (string-to-codepoints($conventionalLine)) return codepoints-to-string($i), codepoints-to-string(8203))"/>
                                                                                        </altova:inline-container-substitute>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                            </fo:table-row>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </xsl:variable>
                                                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:conventionalLineInfo/n1:costCenters)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:variable name="altova:table">
                                                            <fo:table border-collapse="collapse" border-style="none" width="100%" table-layout="fixed">
                                                                <fo:table-column column-width="25%"/>
                                                                <fo:table-column column-width="75%"/>
                                                                <xsl:variable name="altova:CurrContextGrid_30" select="."/>
                                                                <xsl:variable name="altova:ColumnData"/>
                                                                <fo:table-body start-indent="0pt">
                                                                    <xsl:for-each select="n1:conventionalLineInfo">
                                                                        <xsl:for-each select="n1:costCenters">
                                                                            <xsl:variable name="conventionalLine" select="string-join(n1:costCenter/text(), &apos;, &apos;)"/>
                                                                            <fo:table-row>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <xsl:call-template name="ConventionalInfoCostCenterTemplate_L10N"/>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                                            <xsl:value-of select="string-join(for $i in (string-to-codepoints($conventionalLine)) return codepoints-to-string($i), codepoints-to-string(8203))"/>
                                                                                        </altova:inline-container-substitute>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                            </fo:table-row>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </xsl:variable>
                                                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:conventionalLineInfo/n1:projectNumbers)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:variable name="altova:table">
                                                            <fo:table border-collapse="collapse" border-style="none" width="100%" table-layout="fixed">
                                                                <fo:table-column column-width="25%"/>
                                                                <fo:table-column column-width="75%"/>
                                                                <xsl:variable name="altova:CurrContextGrid_31" select="."/>
                                                                <xsl:variable name="altova:ColumnData"/>
                                                                <fo:table-body start-indent="0pt">
                                                                    <xsl:for-each select="n1:conventionalLineInfo">
                                                                        <xsl:for-each select="n1:projectNumbers">
                                                                            <xsl:variable name="conventionalLine" select="string-join(n1:projectNumber/text(), &apos;, &apos;)"/>
                                                                            <fo:table-row>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <xsl:call-template name="ConventionalInfoProjectNumberTemplate_L10N"/>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                                            <xsl:value-of select="string-join(for $i in (string-to-codepoints($conventionalLine)) return codepoints-to-string($i), codepoints-to-string(8203))"/>
                                                                                        </altova:inline-container-substitute>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                            </fo:table-row>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </xsl:variable>
                                                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:conventionalLineInfo/n1:generalLedgerAccountNumbers)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:variable name="altova:table">
                                                            <fo:table border-collapse="collapse" border-style="none" width="100%" table-layout="fixed">
                                                                <fo:table-column column-width="25%"/>
                                                                <fo:table-column column-width="75%"/>
                                                                <xsl:variable name="altova:CurrContextGrid_32" select="."/>
                                                                <xsl:variable name="altova:ColumnData"/>
                                                                <fo:table-body start-indent="0pt">
                                                                    <xsl:for-each select="n1:conventionalLineInfo">
                                                                        <xsl:for-each select="n1:generalLedgerAccountNumbers">
                                                                            <xsl:variable name="conventionalLine" select="string-join(n1:generalLedgerAccountNumber/text(), &apos;, &apos;)"/>
                                                                            <fo:table-row>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <xsl:call-template name="ConventionalInfoGeneralLedgerAccountNumberTemplate_L10N"/>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                                            <xsl:value-of select="string-join(for $i in (string-to-codepoints($conventionalLine)) return codepoints-to-string($i), codepoints-to-string(8203))"/>
                                                                                        </altova:inline-container-substitute>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                            </fo:table-row>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </xsl:variable>
                                                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:conventionalLineInfo/n1:glnNumbersSupplier)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:variable name="altova:table">
                                                            <fo:table border-collapse="collapse" border-style="none" width="100%" table-layout="fixed">
                                                                <fo:table-column column-width="25%"/>
                                                                <fo:table-column column-width="75%"/>
                                                                <xsl:variable name="altova:CurrContextGrid_33" select="."/>
                                                                <xsl:variable name="altova:ColumnData"/>
                                                                <fo:table-body start-indent="0pt">
                                                                    <xsl:for-each select="n1:conventionalLineInfo">
                                                                        <xsl:for-each select="n1:glnNumbersSupplier">
                                                                            <xsl:variable name="conventionalLine" select="string-join(n1:glnNumber/text(), &apos;, &apos;)"/>
                                                                            <fo:table-row>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <xsl:call-template name="ConventionalInfoGlnSupplierNumberTemplate_L10N"/>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                                            <xsl:value-of select="string-join(for $i in (string-to-codepoints($conventionalLine)) return codepoints-to-string($i), codepoints-to-string(8203))"/>
                                                                                        </altova:inline-container-substitute>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                            </fo:table-row>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </xsl:variable>
                                                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:conventionalLineInfo/n1:glnNumbersCustomer)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:variable name="altova:table">
                                                            <fo:table border-collapse="collapse" border-style="none" width="100%" table-layout="fixed">
                                                                <fo:table-column column-width="25%"/>
                                                                <fo:table-column column-width="75%"/>
                                                                <xsl:variable name="altova:CurrContextGrid_34" select="."/>
                                                                <xsl:variable name="altova:ColumnData"/>
                                                                <fo:table-body start-indent="0pt">
                                                                    <xsl:for-each select="n1:conventionalLineInfo">
                                                                        <xsl:for-each select="n1:glnNumbersCustomer">
                                                                            <xsl:variable name="conventionalLine" select="string-join(n1:glnNumber/text(), &apos;, &apos;)"/>
                                                                            <fo:table-row>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <xsl:call-template name="ConventionalInfoGlnCustomerNumberTemplate_L10N"/>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                                            <xsl:value-of select="string-join(for $i in (string-to-codepoints($conventionalLine)) return codepoints-to-string($i), codepoints-to-string(8203))"/>
                                                                                        </altova:inline-container-substitute>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                            </fo:table-row>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </xsl:variable>
                                                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:conventionalLineInfo/n1:materialNumbers)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:variable name="altova:table">
                                                            <fo:table border-collapse="collapse" border-style="none" width="100%" table-layout="fixed">
                                                                <fo:table-column column-width="25%"/>
                                                                <fo:table-column column-width="75%"/>
                                                                <xsl:variable name="altova:CurrContextGrid_35" select="."/>
                                                                <xsl:variable name="altova:ColumnData"/>
                                                                <fo:table-body start-indent="0pt">
                                                                    <xsl:for-each select="n1:conventionalLineInfo">
                                                                        <xsl:for-each select="n1:materialNumbers">
                                                                            <xsl:variable name="conventionalLine" select="string-join(n1:materialNumber/text(), &apos;, &apos;)"/>
                                                                            <fo:table-row>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <xsl:call-template name="ConventionalInfoMaterialNumberTemplate_L10N"/>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                                            <xsl:value-of select="string-join(for $i in (string-to-codepoints($conventionalLine)) return codepoints-to-string($i), codepoints-to-string(8203))"/>
                                                                                        </altova:inline-container-substitute>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                            </fo:table-row>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </xsl:variable>
                                                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:conventionalLineInfo/n1:itemNumbers)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:variable name="altova:table">
                                                            <fo:table border-collapse="collapse" border-style="none" width="100%" table-layout="fixed">
                                                                <fo:table-column column-width="25%"/>
                                                                <fo:table-column column-width="75%"/>
                                                                <xsl:variable name="altova:CurrContextGrid_36" select="."/>
                                                                <xsl:variable name="altova:ColumnData"/>
                                                                <fo:table-body start-indent="0pt">
                                                                    <xsl:for-each select="n1:conventionalLineInfo">
                                                                        <xsl:for-each select="n1:itemNumbers">
                                                                            <xsl:variable name="conventionalLine" select="string-join(n1:itemNumber/text(), &apos;, &apos;)"/>
                                                                            <fo:table-row>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <xsl:call-template name="ConventionalInfoItemNumberTemplate_L10N"/>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                                            <xsl:value-of select="string-join(for $i in (string-to-codepoints($conventionalLine)) return codepoints-to-string($i), codepoints-to-string(8203))"/>
                                                                                        </altova:inline-container-substitute>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                            </fo:table-row>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </xsl:variable>
                                                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:conventionalLineInfo/n1:ekaerIds)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:variable name="altova:table">
                                                            <fo:table border-collapse="collapse" border-style="none" width="100%" table-layout="fixed">
                                                                <fo:table-column column-width="25%"/>
                                                                <fo:table-column column-width="75%"/>
                                                                <xsl:variable name="altova:CurrContextGrid_37" select="."/>
                                                                <xsl:variable name="altova:ColumnData"/>
                                                                <fo:table-body start-indent="0pt">
                                                                    <xsl:for-each select="n1:conventionalLineInfo">
                                                                        <xsl:for-each select="n1:ekaerIds">
                                                                            <xsl:variable name="conventionalLine" select="string-join(n1:ekaerId/text(), &apos;, &apos;)"/>
                                                                            <fo:table-row>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <xsl:call-template name="ConventionalInfoEkaerNumberTemplate_L10N"/>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                                <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" padding="2pt" display-align="center">
                                                                                    <fo:block>
                                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                                            <xsl:value-of select="string-join(for $i in (string-to-codepoints($conventionalLine)) return codepoints-to-string($i), codepoints-to-string(8203))"/>
                                                                                        </altova:inline-container-substitute>
                                                                                    </fo:block>
                                                                                </fo:table-cell>
                                                                            </fo:table-row>
                                                                        </xsl:for-each>
                                                                    </xsl:for-each>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </xsl:variable>
                                                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="exists(n1:additionalLineData)">
                                            <fo:table-row keep-together.within-page="always" keep-together.within-column="always">
                                                <xsl:variable name="sBackground-color">
                                                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                        <xsl:with-param name="sValue" select="if ( n1:lineNumber mod 2 = 0 ) then &quot;#FFFFFF&quot; else &quot;#E8E8E8&quot;"/>
                                                    </xsl:call-template>
                                                </xsl:variable>
                                                <xsl:if test="$sBackground-color != ''">
                                                    <xsl:attribute name="background-color">
                                                        <xsl:value-of select="$sBackground-color"/>
                                                    </xsl:attribute>
                                                </xsl:if>
                                                <fo:table-cell number-columns-spanned="4" border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:variable name="altova:table">
                                                            <fo:table border-collapse="collapse" border-style="none" width="100%" table-layout="fixed" border="solid 1pt gray">
                                                                <fo:table-column column-width="25%"/>
                                                                <fo:table-column column-width="75%"/>
                                                                <xsl:variable name="altova:CurrContextGrid_38" select="."/>
                                                                <xsl:variable name="altova:ColumnData"/>
                                                                <fo:table-body start-indent="0pt">
                                                                    <xsl:for-each select="n1:additionalLineData">
                                                                        <fo:table-row>
                                                                            <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                                                <fo:block>
                                                                                    <xsl:for-each select="n1:dataDescription">
                                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                                            <xsl:apply-templates/>
                                                                                        </altova:inline-container-substitute>
                                                                                    </xsl:for-each>
                                                                                    <fo:inline>
                                                                                        <xsl:text>:</xsl:text>
                                                                                    </fo:inline>
                                                                                    <altova:line-break/>
                                                                                    <fo:inline>
                                                                                        <xsl:text>(</xsl:text>
                                                                                    </fo:inline>
                                                                                    <xsl:for-each select="n1:dataName">
                                                                                        <altova:inline-container-substitute>
                                                                                            <xsl:apply-templates/>
                                                                                        </altova:inline-container-substitute>
                                                                                    </xsl:for-each>
                                                                                    <fo:inline>
                                                                                        <xsl:text>)</xsl:text>
                                                                                    </fo:inline>
                                                                                </fo:block>
                                                                            </fo:table-cell>
                                                                            <fo:table-cell border-left-color="black" border-left-width="0.01in" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                                                <fo:block>
                                                                                    <xsl:for-each select="n1:dataValue">
                                                                                        <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                                                                                    </xsl:for-each>
                                                                                    <fo:inline>
                                                                                        <xsl:text>&#160;</xsl:text>
                                                                                    </fo:inline>
                                                                                </fo:block>
                                                                            </fo:table-cell>
                                                                        </fo:table-row>
                                                                    </xsl:for-each>
                                                                </fo:table-body>
                                                            </fo:table>
                                                        </xsl:variable>
                                                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                    <xsl:choose>
                                        <xsl:when test="position() != last()">
                                            <fo:table-row height="5mm" keep-together.within-page="always" keep-together.within-column="always">
                                                <fo:table-cell number-columns-spanned="4" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block/>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:when>
                                        <xsl:otherwise/>
                                    </xsl:choose>
                                </xsl:for-each>
                            </fo:table-body>
                        </fo:table>
                    </xsl:variable>
                    <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                    <altova:line-break/>
                </xsl:for-each>
            </fo:block>
        </xsl:if>
        <xsl:if test="exists(n1:productFeeSummary)">
            <fo:block keep-together.within-page="always" keep-together.within-column="always" padding-top="5mm" margin-right="100% - 100%" space-before="0" space-after="0" margin="0pt">
                <xsl:call-template name="ProductFeeSummaryTemplate_L10N"/>
                <xsl:variable name="altova:table">
                    <fo:table border-collapse="collapse" border-color="black" border-style="solid" table-omit-header-at-break="true" table-layout="fixed" width="100%" border="solid 1pt gray">
                        <fo:table-column column-width="47.500mm"/>
                        <fo:table-column column-width="47.500mm"/>
                        <fo:table-column column-width="47.500mm"/>
                        <fo:table-column column-width="47.500mm"/>
                        <xsl:variable name="altova:CurrContextGrid_39" select="."/>
                        <xsl:variable name="altova:ColumnData"/>
                        <fo:table-header start-indent="0pt">
                            <fo:table-row>
                                <fo:table-cell border-style="none" font-weight="normal" border="solid 1pt gray" padding="2pt" display-align="center">
                                    <fo:block text-align="left">
                                        <xsl:call-template name="ProductFeeSummaryQuantityTemplate_L10N"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell border-style="none" font-weight="normal" border="solid 1pt gray" padding="2pt" display-align="center">
                                    <fo:block text-align="left">
                                        <xsl:call-template name="ProductFeeRateSummaryTemplate_L10N"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell border-style="none" font-weight="normal" border="solid 1pt gray" padding="2pt" display-align="center">
                                    <fo:block text-align="left">
                                        <xsl:call-template name="ProductFeeAmountSummaryTemplate_L10N"/>
                                    </fo:block>
                                </fo:table-cell>
                                <fo:table-cell border-style="none" font-weight="normal" border="solid 1pt gray" padding="2pt" display-align="center">
                                    <fo:block text-align="left">
                                        <xsl:call-template name="ProductFeeCodeSummaryTemplate_L10N"/>
                                    </fo:block>
                                </fo:table-cell>
                            </fo:table-row>
                        </fo:table-header>
                        <fo:table-body start-indent="0pt">
                            <xsl:for-each select="n1:productFeeSummary">
                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="4" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                        <fo:block>
                                            <xsl:variable name="altova:table">
                                                <fo:table border-collapse="collapse" border-style="none" width="100%" table-layout="fixed">
                                                    <fo:table-column column-width="25%"/>
                                                    <fo:table-column column-width="25%"/>
                                                    <fo:table-column column-width="25%"/>
                                                    <fo:table-column column-width="25%"/>
                                                    <xsl:variable name="altova:CurrContextGrid_40" select="."/>
                                                    <xsl:variable name="altova:ColumnData"/>
                                                    <fo:table-body start-indent="0pt">
                                                        <xsl:for-each select="n1:productFeeData">
                                                            <fo:table-row>
                                                                <fo:table-cell border-style="none" padding="2pt" display-align="center">
                                                                    <fo:block>
                                                                        <xsl:for-each select="n1:productFeeQuantity">
                                                                            <xsl:call-template name="GenericQuantityTypeOffset"/>
                                                                        </xsl:for-each>
                                                                        <fo:inline>
                                                                            <xsl:text>&#160;</xsl:text>
                                                                        </fo:inline>
                                                                        <xsl:for-each select="n1:productFeeMeasuringUnit">
                                                                            <xsl:call-template name="ProductFeeMeasuringUnitTemplate_L10N"/>
                                                                        </xsl:for-each>
                                                                    </fo:block>
                                                                </fo:table-cell>
                                                                <fo:table-cell border-style="none" padding="2pt" display-align="center">
                                                                    <fo:block>
                                                                        <xsl:for-each select="n1:productFeeRate">
                                                                            <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                                        </xsl:for-each>
                                                                        <fo:inline>
                                                                            <xsl:text>&#160;</xsl:text>
                                                                        </fo:inline>
                                                                        <fo:inline font-weight="bold">
                                                                            <xsl:text>HUF / </xsl:text>
                                                                        </fo:inline>
                                                                        <xsl:for-each select="n1:productFeeMeasuringUnit">
                                                                            <xsl:call-template name="ProductFeeMeasuringUnitTemplate_L10N"/>
                                                                        </xsl:for-each>
                                                                    </fo:block>
                                                                </fo:table-cell>
                                                                <fo:table-cell border-style="none" padding="2pt" display-align="center">
                                                                    <fo:block>
                                                                        <xsl:for-each select="n1:productFeeAmount">
                                                                            <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                                        </xsl:for-each>
                                                                        <fo:inline>
                                                                            <xsl:text>&#160;</xsl:text>
                                                                        </fo:inline>
                                                                        <fo:inline font-weight="bold">
                                                                            <xsl:text>HUF</xsl:text>
                                                                        </fo:inline>
                                                                    </fo:block>
                                                                </fo:table-cell>
                                                                <fo:table-cell border-style="none" padding="2pt" display-align="center">
                                                                    <fo:block>
                                                                        <altova:inline-container-substitute font-weight="bold">
                                                                            <xsl:value-of select="string-join(./n1:productFeeCode/*, &apos; - &apos;)"/>
                                                                        </altova:inline-container-substitute>
                                                                    </fo:block>
                                                                </fo:table-cell>
                                                            </fo:table-row>
                                                        </xsl:for-each>
                                                    </fo:table-body>
                                                </fo:table>
                                            </xsl:variable>
                                            <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="4" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                        <fo:block>
                                            <xsl:call-template name="ProductChargeSumTemplate_L10N"/>
                                            <xsl:for-each select="n1:productChargeSum">
                                                <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                            </xsl:for-each>
                                            <fo:inline>
                                                <xsl:text>&#160;</xsl:text>
                                            </fo:inline>
                                            <fo:inline font-weight="bold">
                                                <xsl:text>HUF</xsl:text>
                                            </fo:inline>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell number-columns-spanned="4" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                        <fo:block>
                                            <xsl:call-template name="ProductFeeOperationTemplate_C5X"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <xsl:choose>
                                    <xsl:when test="exists(n1:paymentEvidenceDocumentData)">
                                        <fo:table-row>
                                            <fo:table-cell number-columns-spanned="4" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <xsl:call-template name="PaymentEvidenceDocumentDataTemplate_L10N"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </xsl:when>
                                    <xsl:otherwise/>
                                </xsl:choose>
                                <xsl:choose>
                                    <xsl:when test="exists(n1:paymentEvidenceDocumentData)">
                                        <fo:table-row>
                                            <fo:table-cell number-columns-spanned="4" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <xsl:call-template name="PaymentEvidenceObligatedTemplate_L10N"/>
                                                    <xsl:for-each select="n1:paymentEvidenceDocumentData">
                                                        <xsl:for-each select="n1:obligatedTaxNumber">
                                                            <xsl:call-template name="BaseTaxNumberTemplate"/>
                                                        </xsl:for-each>
                                                        <fo:inline>
                                                            <xsl:text>, </xsl:text>
                                                        </fo:inline>
                                                        <xsl:for-each select="n1:obligatedName">
                                                            <altova:inline-container-substitute font-weight="bold">
                                                                <xsl:apply-templates/>
                                                            </altova:inline-container-substitute>
                                                        </xsl:for-each>
                                                    </xsl:for-each>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </xsl:when>
                                    <xsl:otherwise/>
                                </xsl:choose>
                                <xsl:choose>
                                    <xsl:when test="exists(n1:paymentEvidenceDocumentData)">
                                        <fo:table-row>
                                            <fo:table-cell number-columns-spanned="4" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <xsl:call-template name="PaymentEvidenceObligatedAddressTemplate_L10N"/>
                                                    <xsl:for-each select="n1:paymentEvidenceDocumentData">
                                                        <xsl:for-each select="n1:obligatedAddress">
                                                            <xsl:call-template name="BaseAddressTemplate"/>
                                                        </xsl:for-each>
                                                    </xsl:for-each>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </xsl:when>
                                    <xsl:otherwise/>
                                </xsl:choose>
                                <xsl:choose>
                                    <xsl:when test="exists(n1:paymentEvidenceDocumentData)">
                                        <fo:table-row>
                                            <fo:table-cell number-columns-spanned="2" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <xsl:call-template name="PaymentEvidenceDocumentNoTemplate_L10N"/>
                                                    <altova:line-break/>
                                                    <xsl:for-each select="n1:paymentEvidenceDocumentData">
                                                        <xsl:for-each select="n1:evidenceDocumentNo">
                                                            <altova:inline-container-substitute font-weight="bold">
                                                                <xsl:apply-templates/>
                                                            </altova:inline-container-substitute>
                                                        </xsl:for-each>
                                                    </xsl:for-each>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell number-columns-spanned="2" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <xsl:call-template name="PaymentEvidenceDocumentDateTemplate_L10N"/>
                                                    <altova:line-break/>
                                                    <xsl:for-each select="n1:paymentEvidenceDocumentData">
                                                        <xsl:for-each select="n1:evidenceDocumentDate">
                                                            <xsl:call-template name="GenericDateOffset_Inner"/>
                                                        </xsl:for-each>
                                                    </xsl:for-each>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </xsl:when>
                                    <xsl:otherwise/>
                                </xsl:choose>
                                <xsl:choose>
                                    <xsl:when test="$ProductFeeSummaryCount &gt; 1 and position() != last()">
                                        <fo:table-row>
                                            <fo:table-cell number-columns-spanned="4" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <fo:block text-align="center">
                                                        <fo:leader leader-pattern="rule" rule-thickness="1" leader-length="100%"/>
                                                    </fo:block>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </xsl:when>
                                    <xsl:otherwise/>
                                </xsl:choose>
                                <xsl:choose>
                                    <xsl:when test="$ProductFeeSummaryCount &gt; 1 and position() != last()">
                                        <fo:table-row height="1mm">
                                            <fo:table-cell border-style="none" font-weight="normal" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <xsl:call-template name="ProductFeeSummaryQuantityTemplate_L10N"/>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell border-style="none" font-weight="normal" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <xsl:call-template name="ProductFeeRateSummaryTemplate_L10N"/>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell border-style="none" font-weight="normal" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <xsl:call-template name="ProductFeeAmountSummaryTemplate_L10N"/>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell border-style="none" font-weight="normal" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <xsl:call-template name="ProductFeeCodeSummaryTemplate_L10N"/>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </xsl:when>
                                    <xsl:otherwise/>
                                </xsl:choose>
                            </xsl:for-each>
                        </fo:table-body>
                    </fo:table>
                </xsl:variable>
                <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
            </fo:block>
        </xsl:if>
        <fo:block padding-top="5mm" margin-right="100% - 100%" space-before="0" space-after="0" margin="0pt">
            <xsl:for-each select="n1:invoiceSummary">
                <xsl:choose>
                    <xsl:when test="exists(n1:summaryNormal)">
                        <altova:line-break/>
                        <xsl:variable name="altova:table">
                            <fo:table border-collapse="collapse" border-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" table-layout="fixed" width="100%" border="solid 1pt gray">
                                <fo:table-column column-width="47.500mm"/>
                                <fo:table-column column-width="47.500mm"/>
                                <fo:table-column column-width="47.500mm"/>
                                <fo:table-column column-width="47.500mm"/>
                                <xsl:variable name="altova:CurrContextGrid_41" select="."/>
                                <xsl:variable name="altova:ColumnData"/>
                                <fo:table-header start-indent="0pt">
                                    <fo:table-row>
                                        <fo:table-cell background-color="#E8E8E8" border-bottom-color="black" border-bottom-style="solid" border-bottom-width="0.01in" border-style="none" font-weight="normal" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block text-align="left">
                                                <xsl:call-template name="VatTemplate_L10N"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-bottom-color="black" border-bottom-style="solid" border-bottom-width="0.01in" border-style="none" font-weight="normal" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block text-align="left">
                                                <xsl:call-template name="NetAmountTemplate_L10N"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-bottom-color="black" border-bottom-style="solid" border-bottom-width="0.01in" border-style="none" font-weight="normal" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block text-align="left">
                                                <xsl:call-template name="VatAmountTemplate_L10N"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-bottom-color="black" border-bottom-style="solid" border-bottom-width="0.01in" border-style="none" font-weight="normal" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block text-align="left">
                                                <xsl:call-template name="GrossAmountTemplate_L10N"/>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </fo:table-header>
                                <fo:table-footer start-indent="0pt">
                                    <fo:table-row height="2.5mm">
                                        <fo:table-cell background-color="#E8E8E8" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block/>
                                        </fo:table-cell>
                                        <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block/>
                                        </fo:table-cell>
                                        <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block/>
                                        </fo:table-cell>
                                        <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block/>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <fo:table-row>
                                        <fo:table-cell background-color="#E8E8E8" border-color="black" border-style="none" font-size="9pt" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:call-template name="InvoiceSummarySumTotalTemplate_L10N"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="black" border-style="solid" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:for-each select="n1:summaryNormal">
                                                    <xsl:for-each select="n1:invoiceNetAmount">
                                                        <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                    </xsl:for-each>
                                                </xsl:for-each>
                                                <fo:inline>
                                                    <xsl:text>&#160;</xsl:text>
                                                </fo:inline>
                                                <altova:inline-container-substitute font-weight="bold">
                                                    <xsl:value-of select="$CurrencyCodeValue"/>
                                                </altova:inline-container-substitute>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="black" border-style="solid" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:for-each select="n1:summaryNormal">
                                                    <xsl:for-each select="n1:invoiceVatAmount">
                                                        <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                    </xsl:for-each>
                                                </xsl:for-each>
                                                <fo:inline>
                                                    <xsl:text>&#160;</xsl:text>
                                                </fo:inline>
                                                <altova:inline-container-substitute font-weight="bold">
                                                    <xsl:value-of select="$CurrencyCodeValue"/>
                                                </altova:inline-container-substitute>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-left-color="black" border-style="solid" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <xsl:variable name="sBorder-bottom-color">
                                                <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                    <xsl:with-param name="sValue" select="if (exists(n1:summaryGrossData)) then &apos;black&apos; else &apos;white&apos;"/>
                                                </xsl:call-template>
                                            </xsl:variable>
                                            <xsl:if test="$sBorder-bottom-color != ''">
                                                <xsl:attribute name="border-bottom-color">
                                                    <xsl:value-of select="$sBorder-bottom-color"/>
                                                </xsl:attribute>
                                            </xsl:if>
                                            <xsl:variable name="sBorder-right-color">
                                                <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                    <xsl:with-param name="sValue" select="if (exists(n1:summaryGrossData)) then &apos;black&apos; else &apos;white&apos;"/>
                                                </xsl:call-template>
                                            </xsl:variable>
                                            <xsl:if test="$sBorder-right-color != ''">
                                                <xsl:attribute name="border-right-color">
                                                    <xsl:value-of select="$sBorder-right-color"/>
                                                </xsl:attribute>
                                            </xsl:if>
                                            <xsl:variable name="sBorder-top-color">
                                                <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                    <xsl:with-param name="sValue" select="if (exists(n1:summaryGrossData)) then &apos;black&apos; else &apos;white&apos;"/>
                                                </xsl:call-template>
                                            </xsl:variable>
                                            <xsl:if test="$sBorder-top-color != ''">
                                                <xsl:attribute name="border-top-color">
                                                    <xsl:value-of select="$sBorder-top-color"/>
                                                </xsl:attribute>
                                            </xsl:if>
                                            <fo:block>
                                                <xsl:if test="exists(n1:summaryGrossData)">
                                                    <xsl:for-each select="n1:summaryGrossData">
                                                        <xsl:for-each select="n1:invoiceGrossAmount">
                                                            <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                        </xsl:for-each>
                                                    </xsl:for-each>
                                                    <fo:inline>
                                                        <xsl:text>&#160;</xsl:text>
                                                    </fo:inline>
                                                    <altova:inline-container-substitute font-weight="bold">
                                                        <xsl:value-of select="$CurrencyCodeValue"/>
                                                    </altova:inline-container-substitute>
                                                </xsl:if>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <fo:table-row>
                                        <fo:table-cell background-color="#E8E8E8" border-color="black" border-style="none" font-size="9pt" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:call-template name="InvoiceSummarySumTotalInHUFTemplate_L10N"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="black" border-style="solid" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:for-each select="n1:summaryNormal">
                                                    <xsl:for-each select="n1:invoiceNetAmountHUF">
                                                        <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                    </xsl:for-each>
                                                </xsl:for-each>
                                                <fo:inline>
                                                    <xsl:text>&#160;</xsl:text>
                                                </fo:inline>
                                                <fo:inline font-weight="bold">
                                                    <xsl:text>HUF</xsl:text>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-color="black" border-style="solid" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:for-each select="n1:summaryNormal">
                                                    <xsl:for-each select="n1:invoiceVatAmountHUF">
                                                        <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                    </xsl:for-each>
                                                </xsl:for-each>
                                                <fo:inline font-weight="bold">
                                                    <xsl:text> HUF</xsl:text>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-left-color="black" border-style="solid" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <xsl:variable name="sBorder-bottom-color">
                                                <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                    <xsl:with-param name="sValue" select="if (exists(n1:summaryGrossData)) then &apos;black&apos; else &apos;white&apos;"/>
                                                </xsl:call-template>
                                            </xsl:variable>
                                            <xsl:if test="$sBorder-bottom-color != ''">
                                                <xsl:attribute name="border-bottom-color">
                                                    <xsl:value-of select="$sBorder-bottom-color"/>
                                                </xsl:attribute>
                                            </xsl:if>
                                            <xsl:variable name="sBorder-right-color">
                                                <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                    <xsl:with-param name="sValue" select="if (exists(n1:summaryGrossData)) then &apos;black&apos; else &apos;white&apos;"/>
                                                </xsl:call-template>
                                            </xsl:variable>
                                            <xsl:if test="$sBorder-right-color != ''">
                                                <xsl:attribute name="border-right-color">
                                                    <xsl:value-of select="$sBorder-right-color"/>
                                                </xsl:attribute>
                                            </xsl:if>
                                            <xsl:variable name="sBorder-top-color">
                                                <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                                                    <xsl:with-param name="sValue" select="if (exists(n1:summaryGrossData)) then &apos;black&apos; else &apos;white&apos;"/>
                                                </xsl:call-template>
                                            </xsl:variable>
                                            <xsl:if test="$sBorder-top-color != ''">
                                                <xsl:attribute name="border-top-color">
                                                    <xsl:value-of select="$sBorder-top-color"/>
                                                </xsl:attribute>
                                            </xsl:if>
                                            <fo:block>
                                                <xsl:if test="exists(n1:summaryGrossData)">
                                                    <xsl:for-each select="n1:summaryGrossData">
                                                        <xsl:for-each select="n1:invoiceGrossAmountHUF">
                                                            <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                        </xsl:for-each>
                                                    </xsl:for-each>
                                                    <fo:inline>
                                                        <xsl:text>&#160;</xsl:text>
                                                    </fo:inline>
                                                    <fo:inline font-weight="bold">
                                                        <xsl:text>HUF</xsl:text>
                                                    </fo:inline>
                                                </xsl:if>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </fo:table-footer>
                                <fo:table-body start-indent="0pt">
                                    <xsl:for-each select="n1:summaryNormal">
                                        <xsl:for-each select="n1:summaryByVatRate">
                                            <fo:table-row>
                                                <fo:table-cell background-color="#E8E8E8" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:for-each select="n1:vatRate">
                                                            <xsl:call-template name="VatRateTypeTemplate_C5X"/>
                                                        </xsl:for-each>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:for-each select="n1:vatRateNetData">
                                                            <xsl:for-each select="n1:vatRateNetAmount">
                                                                <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                        <fo:inline>
                                                            <xsl:text>&#160;</xsl:text>
                                                        </fo:inline>
                                                        <altova:inline-container-substitute font-weight="bold">
                                                            <xsl:value-of select="$CurrencyCodeValue"/>
                                                        </altova:inline-container-substitute>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:for-each select="n1:vatRateVatData">
                                                            <xsl:for-each select="n1:vatRateVatAmount">
                                                                <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                        <fo:inline>
                                                            <xsl:text>&#160;</xsl:text>
                                                        </fo:inline>
                                                        <altova:inline-container-substitute font-weight="bold">
                                                            <xsl:value-of select="$CurrencyCodeValue"/>
                                                        </altova:inline-container-substitute>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:if test="exists(n1:vatRateGrossData)">
                                                            <xsl:for-each select="n1:vatRateGrossData">
                                                                <xsl:for-each select="n1:vatRateGrossAmount">
                                                                    <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                                </xsl:for-each>
                                                            </xsl:for-each>
                                                            <fo:inline>
                                                                <xsl:text>&#160;</xsl:text>
                                                            </fo:inline>
                                                            <altova:inline-container-substitute font-weight="bold">
                                                                <xsl:value-of select="$CurrencyCodeValue"/>
                                                            </altova:inline-container-substitute>
                                                        </xsl:if>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                            <fo:table-row>
                                                <fo:table-cell background-color="#E8E8E8" border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:call-template name="InvoiceSummaryInHUFTemplate_L10N"/>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:for-each select="n1:vatRateNetData">
                                                            <xsl:for-each select="n1:vatRateNetAmount">
                                                                <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                        <fo:inline>
                                                            <xsl:text>&#160;</xsl:text>
                                                        </fo:inline>
                                                        <fo:inline font-weight="bold">
                                                            <xsl:text>HUF</xsl:text>
                                                        </fo:inline>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:for-each select="n1:vatRateVatData">
                                                            <xsl:for-each select="n1:vatRateVatAmountHUF">
                                                                <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                            </xsl:for-each>
                                                        </xsl:for-each>
                                                        <fo:inline>
                                                            <xsl:text>&#160;</xsl:text>
                                                        </fo:inline>
                                                        <fo:inline font-weight="bold">
                                                            <xsl:text>HUF</xsl:text>
                                                        </fo:inline>
                                                    </fo:block>
                                                </fo:table-cell>
                                                <fo:table-cell border-style="none" border="solid 1pt gray" padding="2pt" display-align="center">
                                                    <fo:block>
                                                        <xsl:if test="exists(n1:vatRateGrossData)">
                                                            <xsl:for-each select="n1:vatRateGrossData">
                                                                <xsl:for-each select="n1:vatRateGrossAmountHUF">
                                                                    <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                                </xsl:for-each>
                                                            </xsl:for-each>
                                                            <fo:inline>
                                                                <xsl:text>&#160;</xsl:text>
                                                            </fo:inline>
                                                            <fo:inline font-weight="bold">
                                                                <xsl:text>HUF</xsl:text>
                                                            </fo:inline>
                                                        </xsl:if>
                                                    </fo:block>
                                                </fo:table-cell>
                                            </fo:table-row>
                                        </xsl:for-each>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </xsl:variable>
                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                    </xsl:when>
                    <xsl:when test="exists(n1:summarySimplified)">
                        <xsl:variable name="altova:table">
                            <fo:table border-collapse="collapse" border-style="none" table-omit-header-at-break="true" table-layout="fixed" width="100%" border="solid 1pt gray">
                                <fo:table-column column-width="47.500mm"/>
                                <fo:table-column column-width="47.500mm"/>
                                <fo:table-column column-width="47.500mm"/>
                                <fo:table-column column-width="47.500mm"/>
                                <xsl:variable name="altova:CurrContextGrid_42" select="."/>
                                <xsl:variable name="altova:ColumnData"/>
                                <fo:table-header start-indent="0pt">
                                    <fo:table-row>
                                        <fo:table-cell background-color="#E8E8E8" border-bottom-color="black" border-bottom-style="solid" border-bottom-width="0.01in" border-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block text-align="left">
                                                <fo:inline>
                                                    <xsl:text>ÁFA</xsl:text>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-bottom-color="black" border-bottom-style="solid" border-bottom-width="0.01in" border-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block text-align="left"/>
                                        </fo:table-cell>
                                        <fo:table-cell border-bottom-color="black" border-bottom-style="solid" border-bottom-width="0.01in" border-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block text-align="left"/>
                                        </fo:table-cell>
                                        <fo:table-cell border-bottom-color="black" border-bottom-style="solid" border-bottom-width="0.01in" border-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block text-align="left">
                                                <fo:inline>
                                                    <xsl:text>Bruttó összeg</xsl:text>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </fo:table-header>
                                <fo:table-footer start-indent="0pt">
                                    <fo:table-row height="2.5mm">
                                        <fo:table-cell background-color="#E8E8E8" border-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block/>
                                        </fo:table-cell>
                                        <fo:table-cell border-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block/>
                                        </fo:table-cell>
                                        <fo:table-cell border-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block/>
                                        </fo:table-cell>
                                        <fo:table-cell border-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block/>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <fo:table-row>
                                        <fo:table-cell background-color="#E8E8E8" font-size="9pt" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <fo:inline font-weight="bold">
                                                    <xsl:text>Mindösszesen:</xsl:text>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-bottom-style="none" border-right-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block/>
                                        </fo:table-cell>
                                        <fo:table-cell border-bottom-style="none" border-left-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block/>
                                        </fo:table-cell>
                                        <fo:table-cell table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:if test="exists(n1:summaryGrossData)">
                                                    <xsl:for-each select="n1:summaryGrossData">
                                                        <xsl:for-each select="n1:invoiceGrossAmount">
                                                            <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                        </xsl:for-each>
                                                    </xsl:for-each>
                                                    <fo:inline>
                                                        <xsl:text>&#160;</xsl:text>
                                                    </fo:inline>
                                                    <altova:inline-container-substitute font-weight="bold">
                                                        <xsl:value-of select="$CurrencyCodeValue"/>
                                                    </altova:inline-container-substitute>
                                                </xsl:if>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                    <fo:table-row>
                                        <fo:table-cell background-color="#E8E8E8" font-size="9pt" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <fo:inline font-weight="bold">
                                                    <xsl:text>Forintban:</xsl:text>
                                                </fo:inline>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell border-right-style="none" border-top-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block/>
                                        </fo:table-cell>
                                        <fo:table-cell border-left-style="none" border-top-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block/>
                                        </fo:table-cell>
                                        <fo:table-cell table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                            <fo:block>
                                                <xsl:if test="exists(n1:summaryGrossData)">
                                                    <xsl:for-each select="n1:summaryGrossData">
                                                        <xsl:for-each select="n1:invoiceGrossAmountHUF">
                                                            <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                        </xsl:for-each>
                                                    </xsl:for-each>
                                                    <fo:inline>
                                                        <xsl:text>&#160;</xsl:text>
                                                    </fo:inline>
                                                    <fo:inline font-weight="bold">
                                                        <xsl:text>HUF</xsl:text>
                                                    </fo:inline>
                                                </xsl:if>
                                            </fo:block>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </fo:table-footer>
                                <fo:table-body start-indent="0pt">
                                    <xsl:for-each select="n1:summarySimplified">
                                        <fo:table-row>
                                            <fo:table-cell background-color="#E8E8E8" border-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <xsl:for-each select="n1:vatRate">
                                                        <xsl:call-template name="VatRateTypeTemplate_C5X"/>
                                                    </xsl:for-each>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell border-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block/>
                                            </fo:table-cell>
                                            <fo:table-cell border-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <fo:inline>
                                                        <xsl:text>&#160;</xsl:text>
                                                    </fo:inline>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell border-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <xsl:for-each select="n1:vatContentGrossAmount">
                                                        <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                    </xsl:for-each>
                                                    <fo:inline>
                                                        <xsl:text>&#160; </xsl:text>
                                                    </fo:inline>
                                                    <altova:inline-container-substitute font-weight="bold">
                                                        <xsl:value-of select="$CurrencyCodeValue"/>
                                                    </altova:inline-container-substitute>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                        <fo:table-row>
                                            <fo:table-cell background-color="#E8E8E8" border-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <fo:inline>
                                                        <xsl:text>Forintban: </xsl:text>
                                                    </fo:inline>
                                                </fo:block>
                                            </fo:table-cell>
                                            <fo:table-cell border-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block/>
                                            </fo:table-cell>
                                            <fo:table-cell border-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block/>
                                            </fo:table-cell>
                                            <fo:table-cell border-style="none" table-omit-footer-at-break="true" table-omit-header-at-break="true" border="solid 1pt gray" padding="2pt" display-align="center">
                                                <fo:block>
                                                    <xsl:for-each select="n1:vatContentGrossAmountHUF">
                                                        <xsl:call-template name="GenericMonetaryTypeOffset"/>
                                                    </xsl:for-each>
                                                    <fo:inline>
                                                        <xsl:text>&#160;</xsl:text>
                                                    </fo:inline>
                                                    <fo:inline font-weight="bold">
                                                        <xsl:text>HUF</xsl:text>
                                                    </fo:inline>
                                                </fo:block>
                                            </fo:table-cell>
                                        </fo:table-row>
                                    </xsl:for-each>
                                </fo:table-body>
                            </fo:table>
                        </xsl:variable>
                        <xsl:apply-templates select="$altova:table" mode="altova:copy-table"/>
                    </xsl:when>
                </xsl:choose>
                <altova:line-break/>
            </xsl:for-each>
        </fo:block>
    </xsl:template>
