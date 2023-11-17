    <xsl:template name="InvoiceClassificationTemplate_C5X">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Bizonylat típusa</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Document type</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Dokumentenart</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
        <fo:inline>
            <xsl:text>: </xsl:text>
        </fo:inline>
        <xsl:choose>
            <xsl:when test="not(exists($XML/n1:InvoiceData/n1:invoiceMain/n1:invoice/n1:invoiceReference)) and not(exists($XML/n1:InvoiceData/n1:invoiceMain/n1:batchInvoice))">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Számla</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Invoice</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Rechnung</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="(exists($XML/n1:InvoiceData/n1:invoiceMain/n1:invoice/n1:invoiceReference) or exists($XML/n1:InvoiceData/n1:invoiceMain/n1:batchInvoice))">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Számlával egy tekintet alá eső okirat</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Invoice equivalent document</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Rechnungsäquivalentes Dokument</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="InvoiceNumberTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Bizonylat sorszáma: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Serial number of the document: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Seriennummer des Dokuments: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="HeaderTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Számlaadat</xsl:text>
                </fo:inline>
                <xsl:if test="n1:InvoiceData/n1:completenessIndicator = false()">
                    <fo:inline font-weight="bold">
                        <xsl:text>-szolgáltatás</xsl:text>
                    </fo:inline>
                </xsl:if>
                <fo:inline font-weight="bold">
                    <xsl:text> képi megjelenítése</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Visual display of invoice data</xsl:text>
                </fo:inline>
                <xsl:if test="n1:InvoiceData/n1:completenessIndicator = false()">
                    <fo:inline font-weight="bold">
                        <xsl:text> report</xsl:text>
                    </fo:inline>
                </xsl:if>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Visuelle Anzeige </xsl:text>
                </fo:inline>
                <xsl:choose>
                    <xsl:when test="n1:InvoiceData/n1:completenessIndicator = true()">
                        <fo:inline font-weight="bold">
                            <xsl:text>von Rechnungsdaten</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="n1:InvoiceData/n1:completenessIndicator = false()">
                        <fo:inline font-weight="bold">
                            <xsl:text>des Rechnungsdatenberichts</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="FooterTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Ez a dokumentum a bizonylat adattartalmának standard képi megjelenítése. Ez az dokumentum nem a számla.</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>This document is a standard image representation of the data content of the document. This document is not the invoice.</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Dieses Dokument ist eine Standardbilddarstellung des Dateninhalts des Dokuments. Dieses Dokument ist nicht die Rechnung.</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="InvoiceIssueDateTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Bizonylat kiállítási dátuma: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Document issue date: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Dokumentausgabedatum: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="BatchIndexTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Módosítás sorszáma a kötegen belül: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Sequence number of the modification document within the batch: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Laufende Nummer des Änderungsdokuments innerhalb der Charge: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="GenericDateOffset_Outer">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:value-of select="concat(year-from-date(.), &apos;. &apos;, (
if (month-from-date(.) = 01) then &apos;január&apos; else
    if (month-from-date(.) = 02) then &apos;február&apos; else
        if (month-from-date(.) = 03) then &apos;március&apos; else
            if (month-from-date(.) = 04) then &apos;április&apos; else
                if (month-from-date(.) = 05) then &apos;május&apos; else
                    if (month-from-date(.) = 06) then &apos;június&apos; else
                        if (month-from-date(.) = 07) then &apos;július&apos; else
                            if (month-from-date(.) = 08) then &apos;augusztus&apos; else
                                if (month-from-date(.) = 09) then &apos;szeptember&apos; else
                                    if (month-from-date(.) = 10) then &apos;október&apos; else
                                        if (month-from-date(.) = 11) then &apos;november&apos;
                                             else &apos;december&apos;),
&apos; &apos;, day-from-date(.), &apos;.&apos;)"/>
                </altova:inline-container-substitute>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:value-of select="concat(day-from-date(.), &apos;. &apos;, (
if (month-from-date(.) = 01) then &apos;January&apos; else
    if (month-from-date(.) = 02) then &apos;February&apos; else
        if (month-from-date(.) = 03) then &apos;March&apos; else
            if (month-from-date(.) = 04) then &apos;April&apos; else
                if (month-from-date(.) = 05) then &apos;May&apos; else
                    if (month-from-date(.) = 06) then &apos;June&apos; else
                        if (month-from-date(.) = 07) then &apos;July&apos; else
                            if (month-from-date(.) = 08) then &apos;August&apos; else
                                if (month-from-date(.) = 09) then &apos;September&apos; else
                                    if (month-from-date(.) = 10) then &apos;October&apos; else
                                        if (month-from-date(.) = 11) then &apos;November&apos;
                                             else &apos;December&apos;),
&apos; &apos;, year-from-date(.), &apos;.&apos;)"/>
                </altova:inline-container-substitute>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:value-of select="concat(year-from-date(.), &apos;. &apos;, (
if (month-from-date(.) = 01) then &apos;Januar&apos; else
    if (month-from-date(.) = 02) then &apos;Februar&apos; else
        if (month-from-date(.) = 03) then &apos;März&apos; else
            if (month-from-date(.) = 04) then &apos;April&apos; else
                if (month-from-date(.) = 05) then &apos;Mai&apos; else
                    if (month-from-date(.) = 06) then &apos;Juni&apos; else
                        if (month-from-date(.) = 07) then &apos;Juli&apos; else
                            if (month-from-date(.) = 08) then &apos;August&apos; else
                                if (month-from-date(.) = 09) then &apos;September&apos; else
                                    if (month-from-date(.) = 10) then &apos;Oktober&apos; else
                                        if (month-from-date(.) = 11) then &apos;November&apos;
                                             else &apos;Dezember&apos;),
&apos; &apos;, day-from-date(.), &apos;.&apos;)"/>
                </altova:inline-container-substitute>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="NamespaceErrorTemplate_L10N">
        <altova:line-break/>
        <fo:inline>
            <xsl:text>Feldolgozási hiba! / Processing error! / Verarbeitungsfehler! </xsl:text>
        </fo:inline>
        <altova:line-break/>
        <altova:line-break/>
        <altova:line-break/>
        <altova:line-break/>
        <altova:line-break/>
        <altova:line-break/>
        <altova:line-break/>
        <fo:inline>
            <xsl:text>A bemenetként megadott XML állomány nem az Online Számla adatszolgáltatás 3.0-ás verziója szerint készült. Az XSLT sablon desktop verziója nem alkalmas ettől eltérő képi megjelenítés generálására. (A default namespace értéke nem a 3.0-ás számla adatszolgáltatásokban használt &apos;http://schemas.nav.gov.hu/OSA/3.0/data&apos;.)</xsl:text>
        </fo:inline>
        <altova:line-break/>
        <altova:line-break/>
        <fo:inline>
            <xsl:text>The XML file specified as input was not created according to Online Invoice Data Report version 3.0. The desktop version of the XSLT template is not suitable for generating a different image display. (The default namespace value is not &apos;http://schemas.nav.gov.hu/OSA/3.0/data&apos;.</xsl:text>
        </fo:inline>
        <altova:line-break/>
        <altova:line-break/>
        <fo:inline>
            <xsl:text>Die als Eingabe angegebene XML-Datei wurde nicht gemäß Online Invoice Data Report Version 3.0 erstellt. Die Desktop-Version des XSLT-Templates ist nicht geeignet, um eine andere Bilddarstellung zu erzeugen. (Der Standard-Namespace-Wert ist nicht „http://schemas.nav.gov.hu/OSA/3.0/data“.</xsl:text>
        </fo:inline>
    </xsl:template>
    <xsl:template name="DisplayErrorTemplate_L10N">
        <fo:inline>
            <xsl:text>Feldolgozási hiba / Processing error! / Verarbeitungsfehler! </xsl:text>
        </fo:inline>
        <altova:line-break/>
        <altova:line-break/>
        <altova:line-break/>
        <altova:line-break/>
        <altova:line-break/>
        <altova:line-break/>
        <altova:line-break/>
        <fo:inline>
            <xsl:text>A dokumentum megjelenítési beállításait tartalmazó $lang változó nem került helyesen megadásra! A támogatott értékek: HU, EN, DE. / The $lang variable containing the display options of the document is not correctly provided! Supported values are: HU, EN, DE. / Das $lang-Variable, dass die Anzeigeoptionen des Dokuments enthält, wird nicht bereitgestellt! Unterstützte Werte sind: HU, EN, DE. </xsl:text>
        </fo:inline>
    </xsl:template>
    <xsl:template name="InvoiceCategoryTemplate">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Számla típusa: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Type of invoice: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>RechnungsTyp: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
        <xsl:choose>
            <xsl:when test="n1:invoiceCategory = &apos;NORMAL&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Normál számla</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Regular invoice</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Normales Rechnung</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="n1:invoiceCategory = &apos;SIMPLIFIED&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Egyszerűsített számla</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Simplified invoice</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Vereinfachte Rechnung</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="n1:invoiceCategory = &apos;AGGREGATE&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Gyűjtő számla</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Aggregated invoice</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Aggregierte Rechnung</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="InvoiceAppearanceTemplate">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Megjelenési forma: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Invoice appearance: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Erscheinungsform: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
        <xsl:choose>
            <xsl:when test="n1:invoiceAppearance = &apos;PAPER&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Papír</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Paper</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Papier</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="n1:invoiceAppearance = &apos;ELECTRONIC&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Elektronikus</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Electronic</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Electronish</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="n1:invoiceAppearance = &apos;EDI&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>EDI</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="n1:invoiceAppearance = &apos;UNKNOWN&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Ismeretlen</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Unknown</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Unbekannt</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="PeriodicalSettlementTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Folyamatos teljesítésű ügylet</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Periodic settlement</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Periodische Abrechnung</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="PaymentMethodTemplate_C5X">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Fizetési mód: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Payment method: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Zahlungsweise: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
        <xsl:choose>
            <xsl:when test="n1:paymentMethod = &apos;TRANSFER&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Átutalás</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Bank transfer</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Banküberweisung</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="n1:paymentMethod = &apos;CASH&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Készpénz</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Cash</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Bargeld</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="n1:paymentMethod = &apos;CARD&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Kártya</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Card</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Karte</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="n1:paymentMethod = &apos;VOUCHER&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Utalvány, váltó</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Voucher, bill of exchange</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Gutschein, Weschel</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="n1:paymentMethod = &apos;OTHER&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Egyéb</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Other</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Sonstiges</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="CashAccountingIndicatorTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Pénzforgalmi elszámolás</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Cash accounting</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Verrechnung des Geldverkehrs</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="SelfBillingIncidatorTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Önszámlázás</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Self billing</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Selbstfakturierung</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="UtilitySettlementIndicatorTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Közmű elszámoló számla</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Public utility settlement invoice</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Verrechnungskonto für Versorgungsunternehmen</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="OriginalInvoiceNumberTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Módosított számla sorszáma: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Original invoice reference number: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Original Rechnungsreferenznummer: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ModificationIndexTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Módosítás sorszáma: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Modification index: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Änderungsindex: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="SellerTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>ELADÓ</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>SELLER</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>VERKÄUFER</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="BuyerTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>VEVŐ</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>CUSTOMER</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>KUNDE</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="TaxNumberTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Adószám: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Tax number: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Steuernummer: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="TaxNumberGroupTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Csoportazonosító szám: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Group identifier number: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Gruppenidentifikationsnummer: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="CommunityVatNumberTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Közösségi adószám: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>EU VAT number: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>EU-Steuernummer: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ThirdStateTaxIdTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Harmadik országbeli adóazonosító: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Third state tax ID: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Dritte staatliche Steuer ID: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="GroupMemberTaxNumberTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Csoporttag adószáma: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Group member tax number: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Steuernummer des Gruppenmitglieds: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="InvoiceHeadNameTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Név: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Name: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Name: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="InvoiceHeadAddressTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Cím: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Address: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Anschrift: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="FiscalRepresentativeNameTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Pénzügyi képviselő neve: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Fiscal representative&apos;s name: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Name des Finanzvertreters: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="InvoiceDeliveryDateTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Teljesítés dátuma: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Delivery date: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Lieferdatum: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="InvoiceAccountingDeliveryDateTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Számviteli teljesítés dátuma: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Accounting delivery date: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Abrechnungsdatum: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="InvoiceDeliveryPeriodStartTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Teljesítési időszak kezdete: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Start of delivery period: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Beginn der Lieferfrist: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="InvoiceDeliveryPeriodEndTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Teljesítési időszak vége: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>End of delivery date: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Ende der Lieferfrist: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="PaymentDateTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Fizetési határidő: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Payment date: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Zahlungsfrist: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="CurrencyCodeTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Számla pénzneme: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Invoice currency: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Währung der Rechnung: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ExchangeRateTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Átváltási árfolyam: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Exchange rate: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Tauschrate: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ConventionalInfoOrderNumberTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Megrendelés szám(ok):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Order number(s):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Bestellnummer(n):</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ConventionalInfoDeliveryNoteTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Szállítólevél szám(ok):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Delivery note(s):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Lieferschein(en):</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ConventionalInfoShippingDateTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Szállítási dátum(ok):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Shipping date(s):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Versanddatum (daten):</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="GenericDateOffset_Inner">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:value-of select="concat(year-from-date(.), &apos;. &apos;, (
if (month-from-date(.) = 01) then &apos;január&apos; else
    if (month-from-date(.) = 02) then &apos;február&apos; else
        if (month-from-date(.) = 03) then &apos;március&apos; else
            if (month-from-date(.) = 04) then &apos;április&apos; else
                if (month-from-date(.) = 05) then &apos;május&apos; else
                    if (month-from-date(.) = 06) then &apos;június&apos; else
                        if (month-from-date(.) = 07) then &apos;július&apos; else
                            if (month-from-date(.) = 08) then &apos;augusztus&apos; else
                                if (month-from-date(.) = 09) then &apos;szeptember&apos; else
                                    if (month-from-date(.) = 10) then &apos;október&apos; else
                                        if (month-from-date(.) = 11) then &apos;november&apos;
                                             else &apos;december&apos;),
&apos; &apos;, day-from-date(.), &apos;.&apos;)"/>
                </altova:inline-container-substitute>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:value-of select="concat(day-from-date(.), &apos;. &apos;, (
if (month-from-date(.) = 01) then &apos;January&apos; else
    if (month-from-date(.) = 02) then &apos;February&apos; else
        if (month-from-date(.) = 03) then &apos;March&apos; else
            if (month-from-date(.) = 04) then &apos;April&apos; else
                if (month-from-date(.) = 05) then &apos;May&apos; else
                    if (month-from-date(.) = 06) then &apos;June&apos; else
                        if (month-from-date(.) = 07) then &apos;July&apos; else
                            if (month-from-date(.) = 08) then &apos;August&apos; else
                                if (month-from-date(.) = 09) then &apos;September&apos; else
                                    if (month-from-date(.) = 10) then &apos;October&apos; else
                                        if (month-from-date(.) = 11) then &apos;November&apos;
                                             else &apos;December&apos;),
&apos; &apos;, year-from-date(.), &apos;.&apos;)"/>
                </altova:inline-container-substitute>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:value-of select="concat(year-from-date(.), &apos;. &apos;, (
if (month-from-date(.) = 01) then &apos;Januar&apos; else
    if (month-from-date(.) = 02) then &apos;Februar&apos; else
        if (month-from-date(.) = 03) then &apos;März&apos; else
            if (month-from-date(.) = 04) then &apos;April&apos; else
                if (month-from-date(.) = 05) then &apos;Mai&apos; else
                    if (month-from-date(.) = 06) then &apos;Juni&apos; else
                        if (month-from-date(.) = 07) then &apos;Juli&apos; else
                            if (month-from-date(.) = 08) then &apos;August&apos; else
                                if (month-from-date(.) = 09) then &apos;September&apos; else
                                    if (month-from-date(.) = 10) then &apos;Oktober&apos; else
                                        if (month-from-date(.) = 11) then &apos;November&apos;
                                             else &apos;Dezember&apos;),
&apos; &apos;, day-from-date(.), &apos;.&apos;)"/>
                </altova:inline-container-substitute>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="AggregateInstructionsTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Ld: tételsorok!</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>See invoice lines!</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Siehe Rechnungspositionen!</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="PrivatePersonTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline color="red" font-weight="bold">
                    <xsl:text>A vevő nem áfaalany természetes személy, ezért adatai az adatszolgáltatásban nem szerepelnek.</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline color="red" font-weight="bold">
                    <xsl:text>The customer is non-VAT liable natural person and therefore his / her data is not included in the data report.</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline color="red" font-weight="bold">
                    <xsl:text>Der Kunde ist eine nicht umsatzsteuerpflichtige natürliche Person und daher werden seine Daten nicht in den Rechnungsdatenberichts aufgenommen.</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="SmallBusinessIndicatorTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>(kisadózó)</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>(low-tax bracket enterprise)</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>(kleinunternehmer)</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="IndividualExemptionTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>(alanyi áfamentes)</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>(individual VAT exempt)</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>(Individuell steuerfrei)</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="BankAccountNumberTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Bankszámlaszám: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Bank account number: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Bankkontonummer: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ExciseLicensNumberTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Jövedéki engedély száma: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Excise license number: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Verbrauchsteuer-Lizenznummer: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ConventionalInfoContractNumberTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Szerződésszám(ok):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Contract number(s):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Vertragsnummer(n):</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ConventionalInfoSupplierCompanyCodeTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Eladó vállalati kódja(i):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Supplier company code(s):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Buchungskreise(n) des Verkäufer:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ConventionalInfoCustomerCompanyCodeTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Vevő vállalati kódja(i):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Customer company codes:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Buchungskreise(n) des Kunden:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ConventionalInfoDealerCodeTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Beszállító kód(ok):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Dealer code(s):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Händlercode(n):</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ConventionalInfoCostCenterTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Költséghely(ek):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Cost center(s):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Kostenstelle(n):</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ConventionalInfoProjectNumberTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Projektszám(ok):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Project number(s):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Projektnummer(n):</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ConventionalInfoGeneralLedgerAccountNumberTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Főkönyvi számlaszám(ok)</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>General ledger account number(s)</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Hauptbuchkontonummer(n)</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ConventionalInfoGlnSupplierNumberTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Kiállítói globális helyazonosító szám(ok):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Supplier global location number(s):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Globale Standortnummer(n) des Verkäufer:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ConventionalInfoGlnCustomerNumberTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Vevői globális helyazonosító szám(ok):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Customer global location number(s):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Globale Standortnummer(n) des Kunden:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ConventionalInfoMaterialNumberTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Anyagszám(ok):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Material number(s):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Materialnummer(n):</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ConventionalInfoItemNumberTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Cikkszám(ok):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Item number(s):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Artikelnummer(n):</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ConventionalInfoEkaerNumberTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>EKÁER azonosító(k):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>EKAER ID(s):</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>EKAER ID(s):</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="GenericQuantityTypeOffset">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:value-of select="translate(replace(format-number(., &apos;###,###,###,##0.##########&apos;), &apos;,&apos;, &apos; &apos;), &apos;.&apos;, &apos;,&apos;)"/>
                </altova:inline-container-substitute>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:value-of select="format-number(., &apos;###,###,###,##0.##########&apos;)"/>
                </altova:inline-container-substitute>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:value-of select="translate(translate(replace(format-number(., &apos;###,###,###,##0.##########&apos;), &apos;,&apos;, &apos; &apos;), &apos;.&apos;, &apos;,&apos;), &apos; &apos;, &apos;.&apos;)"/>
                </altova:inline-container-substitute>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="GenericMonetaryTypeOffset">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:value-of select="translate(replace(format-number(., &apos;#,###,###,###,###,##0.##&apos;), &apos;,&apos;, &apos; &apos;), &apos;.&apos;, &apos;,&apos;)"/>
                </altova:inline-container-substitute>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:value-of select="format-number(., &apos;#,###,###,###,###,##0.##&apos;)"/>
                </altova:inline-container-substitute>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:value-of select="translate(translate(replace(format-number(., &apos;#,###,###,###,###,##0.##&apos;), &apos;,&apos;, &apos; &apos;), &apos;.&apos;, &apos;,&apos;), &apos; &apos;, &apos;.&apos;)"/>
                </altova:inline-container-substitute>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="GenericZeroWidthWhitespaceOffset">
        <altova:inline-container-substitute font-weight="bold">
            <xsl:value-of select="string-join(for $i in (string-to-codepoints(.)) return codepoints-to-string($i), codepoints-to-string(8203))"/>
        </altova:inline-container-substitute>
    </xsl:template>
    <xsl:template name="VatRateTypeTemplate_C5X">
        <xsl:choose>
            <xsl:when test="exists(n1:vatPercentage)">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline>
                            <xsl:text>ÁFA mérték</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline>
                            <xsl:text>VAT percentage</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline>
                            <xsl:text>MwSt Prozentsatz</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
                <fo:inline>
                    <xsl:text>: </xsl:text>
                </fo:inline>
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:value-of select="n1:vatPercentage * 100"/>
                </altova:inline-container-substitute>
                <fo:inline font-weight="bold">
                    <xsl:text> %</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="exists(n1:vatContent)">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline>
                            <xsl:text>ÁFA tartalom</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline>
                            <xsl:text>VAT content</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline>
                            <xsl:text>MwSt Inhalt</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
                <fo:inline>
                    <xsl:text>: </xsl:text>
                </fo:inline>
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:value-of select="n1:vatContent * 100"/>
                </altova:inline-container-substitute>
                <fo:inline font-weight="bold">
                    <xsl:text> %</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="exists(n1:vatExemption)">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline>
                            <xsl:text>ÁFA</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline>
                            <xsl:text>VAT</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline>
                            <xsl:text>MwSt</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
                <fo:inline>
                    <xsl:text>: </xsl:text>
                </fo:inline>
                <xsl:for-each select="n1:vatExemption">
                    <xsl:for-each select="n1:case">
                        <altova:inline-container-substitute font-weight="bold">
                            <xsl:apply-templates/>
                        </altova:inline-container-substitute>
                    </xsl:for-each>
                </xsl:for-each>
            </xsl:when>
            <xsl:when test="exists(n1:vatOutOfScope)">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline>
                            <xsl:text>ÁFA</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline>
                            <xsl:text>VAT</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline>
                            <xsl:text>MwSt</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
                <fo:inline>
                    <xsl:text>: </xsl:text>
                </fo:inline>
                <xsl:for-each select="n1:vatOutOfScope">
                    <xsl:for-each select="n1:case">
                        <altova:inline-container-substitute font-weight="bold">
                            <xsl:apply-templates/>
                        </altova:inline-container-substitute>
                    </xsl:for-each>
                </xsl:for-each>
            </xsl:when>
            <xsl:when test="n1:vatDomesticReverseCharge = true()">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline>
                            <xsl:text>ÁFA</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline>
                            <xsl:text>VAT</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline>
                            <xsl:text>MwSt</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
                <fo:inline>
                    <xsl:text>: </xsl:text>
                </fo:inline>
                <fo:inline font-weight="bold">
                    <xsl:text>FAD</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="exists(n1:marginSchemeIndicator)">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline>
                            <xsl:text>ÁFA</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline>
                            <xsl:text>VAT</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline>
                            <xsl:text>MwSt</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
                <fo:inline>
                    <xsl:text>: </xsl:text>
                </fo:inline>
                <xsl:choose>
                    <xsl:when test="n1:marginSchemeIndicator = &apos;TRAVEL_AGENCY&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>KAU</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="n1:marginSchemeIndicator = &apos;SECOND_HAND&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>KAH</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="n1:marginSchemeIndicator = &apos;ARTWORK&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>KAM</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="n1:marginSchemeIndicator = &apos;ANTIQUES&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>KAR</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="exists(n1:vatAmountMismatch)">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline>
                            <xsl:text>ÁFA</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline>
                            <xsl:text>VAT</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline>
                            <xsl:text>MwSt</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
                <fo:inline>
                    <xsl:text>: </xsl:text>
                </fo:inline>
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:value-of select="n1:vatAmountMismatch/n1:vatRate * 100"/>
                </altova:inline-container-substitute>
                <fo:inline>
                    <xsl:text>&#160;</xsl:text>
                </fo:inline>
                <fo:inline font-weight="bold">
                    <xsl:text>%</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="n1:noVatCharge = true()">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline>
                            <xsl:text>ÁFA</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline>
                            <xsl:text>VAT</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline>
                            <xsl:text>MwSt</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
                <fo:inline>
                    <xsl:text>: </xsl:text>
                </fo:inline>
                <fo:inline font-weight="bold">
                    <xsl:text>NFA</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="VatReasonTemplate_C5X">
        <xsl:choose>
            <xsl:when test="exists(n1:vatExemption/n1:reason)">
                <xsl:for-each select="n1:vatExemption">
                    <xsl:for-each select="n1:reason">
                        <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                    </xsl:for-each>
                </xsl:for-each>
            </xsl:when>
            <xsl:when test="exists(n1:vatOutOfScope/n1:reason)">
                <xsl:for-each select="n1:vatOutOfScope">
                    <xsl:for-each select="n1:reason">
                        <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                    </xsl:for-each>
                </xsl:for-each>
            </xsl:when>
            <xsl:when test="n1:vatDomesticReverseCharge = true()">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Belföldi fordított adózás</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Domestic reverse charging</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Umgekehrte Gebühr im Inland</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="exists(n1:marginSchemeIndicator)">
                <xsl:choose>
                    <xsl:when test="n1:marginSchemeIndicator = &apos;TRAVEL_AGENCY&apos;">
                        <xsl:choose>
                            <xsl:when test="$lang = &apos;HU&apos;">
                                <fo:inline font-weight="bold">
                                    <xsl:text>Különbözet szerinti szabályozás - utazási irodák</xsl:text>
                                </fo:inline>
                            </xsl:when>
                            <xsl:when test="$lang = &apos;EN&apos;">
                                <fo:inline font-weight="bold">
                                    <xsl:text>Margin scheme taxation - travel agencies</xsl:text>
                                </fo:inline>
                            </xsl:when>
                            <xsl:when test="$lang = &apos;DE&apos;">
                                <fo:inline font-weight="bold">
                                    <xsl:text>Differenzbesteuerung - Reiseagenturen</xsl:text>
                                </fo:inline>
                            </xsl:when>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:when test="n1:marginSchemeIndicator = &apos;SECOND_HAND&apos;">
                        <xsl:choose>
                            <xsl:when test="$lang = &apos;HU&apos;">
                                <fo:inline font-weight="bold">
                                    <xsl:text>Különbözet szerinti szabályozás - használt cikkek</xsl:text>
                                </fo:inline>
                            </xsl:when>
                            <xsl:when test="$lang = &apos;EN&apos;">
                                <fo:inline font-weight="bold">
                                    <xsl:text>Margin scheme taxation - second hand goods</xsl:text>
                                </fo:inline>
                            </xsl:when>
                            <xsl:when test="$lang = &apos;DE&apos;">
                                <fo:inline font-weight="bold">
                                    <xsl:text>Differenzbesteuerung - Gebrauchtwaren</xsl:text>
                                </fo:inline>
                            </xsl:when>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:when test="n1:marginSchemeIndicator = &apos;ARTWORK&apos;">
                        <xsl:choose>
                            <xsl:when test="$lang = &apos;HU&apos;">
                                <fo:inline font-weight="bold">
                                    <xsl:text>Különbözet szerinti szabályozás - műalkotások</xsl:text>
                                </fo:inline>
                            </xsl:when>
                            <xsl:when test="$lang = &apos;EN&apos;">
                                <fo:inline font-weight="bold">
                                    <xsl:text>Margin scheme taxation - works of art</xsl:text>
                                </fo:inline>
                            </xsl:when>
                            <xsl:when test="$lang = &apos;DE&apos;">
                                <fo:inline font-weight="bold">
                                    <xsl:text>Differenzbesteuerung - Kunstwerke</xsl:text>
                                </fo:inline>
                            </xsl:when>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:when test="n1:marginSchemeIndicator = &apos;ANTIQUES&apos;">
                        <xsl:choose>
                            <xsl:when test="$lang = &apos;HU&apos;">
                                <fo:inline font-weight="bold">
                                    <xsl:text>Különbözet szerinti szabályozás - gyűjteménydarabok és régiségek</xsl:text>
                                </fo:inline>
                            </xsl:when>
                            <xsl:when test="$lang = &apos;EN&apos;">
                                <fo:inline font-weight="bold">
                                    <xsl:text>Margin scheme taxation - collector&apos;s item and antiques</xsl:text>
                                </fo:inline>
                            </xsl:when>
                            <xsl:when test="$lang = &apos;DE&apos;">
                                <fo:inline font-weight="bold">
                                    <xsl:text>Differenzbesteuerung - Sammlerstücke und Antiquitäten</xsl:text>
                                </fo:inline>
                            </xsl:when>
                        </xsl:choose>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="exists(n1:vatAmountMismatch)">
                <xsl:for-each select="n1:vatAmountMismatch">
                    <xsl:for-each select="n1:case">
                        <altova:inline-container-substitute font-weight="bold">
                            <xsl:apply-templates/>
                        </altova:inline-container-substitute>
                    </xsl:for-each>
                </xsl:for-each>
            </xsl:when>
            <xsl:when test="n1:noVatCharge = true()">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Nincs felszámított adó az ÁFA törvény 17.§ alapján.</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>No VAT is charged under Section 17 of the VAT Act.</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Nach § 17 des Mehrwertsteuergesetzes wird keine Mehrwertsteuer erhoben.</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="MergedItemTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Az adatszolgáltatás összevont soradatokat tartalmaz!</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>The invoice data report contains merged line data!</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Der Rechnungsdatenbericht enthält zusammengeführte Zeilendaten!</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="LineNumberTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Tétel sorszám: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Line number: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Zeilennummer: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="LineOperationCreateTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Eredeti számlán létrehozott új tétel sorszáma: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Line number of newly created item on original invoice: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Zeilennummer des neu erstellten Artikels auf der Originalrechnung: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="LineOperationModifyTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Eredeti számla módosítással érintett tétel sorszáma: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Line number of modified item on original invoice: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Zeilennummer des geänderten Artikels auf der Originalrechnung: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ReferenceToOtherLineTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Kapcsolódó tételek: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Referenced lines: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Referenzierte Zeilen: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="AdvanceIndicatorTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>(Előleg jellegű tétel)</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>(Advance payment type)</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>(Vorauszahlungsart)</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="LineDescriptionTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Megnevezés: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Description: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Beschreibung: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="QuantityTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Mennyiség:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Quantity:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Menge:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="UnitOfMeasureTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Mennyiségi egység:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Unit of measure:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Mengeneinheit:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="LineDeliveryDateTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Tétel teljesítési dátuma:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Line delivery date:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Lieferdatum der Linie:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="LineExchangeRateTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Tétel átváltási árfolyama:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Line exchange rate:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Wechselkurs der Linie:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="UnitPriceTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Egységár:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Unit price:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Einheitpreis:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="NetAmountTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Nettó összeg:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Net amount:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Nettobetrag:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="VatAmountTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Áfa összeg:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>VAT amount:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>MwSt Betrag:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="GrossAmountTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Bruttó összeg:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Gross amount:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Bruttowert:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="VatDescriptionTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Áfa eset leírása: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>VAT case description: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Beschreibung des Mwst: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="UnitPriceHUFTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Egységár forintban:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Unit price in HUF:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Einheitpreis im HUF:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="LineNetAmountHUFTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Nettó forintban:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Net amount in HUF:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Nettobetrag im HUF:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="LineVatAmountHUFTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Áfa forintban:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>VAT amount in HUF:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>MwSt Betrag im HUF:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="LineGrossAmountHUFTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Bruttó forintban:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Gross amount in HUF:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Bruttowert im HUF:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="LineDiscountDataTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline text-decoration="underline">
                    <xsl:text>Tételsor árengedmény adatok</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline text-decoration="underline">
                    <xsl:text>Line discount data</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline text-decoration="underline">
                    <xsl:text>Rabattdaten der Linie</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="LineDiscountDescriptionTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Leírása:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Description:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Bezeichnung:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="LineDiscountValueTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Összege:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Sum:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Summe:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="LineDiscountRateTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Aránya:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Rate:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Bewertung:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="AdvancePaymentTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline text-decoration="underline">
                    <xsl:text>Előleg beszámítás adatok</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline text-decoration="underline">
                    <xsl:text>Advance payment data</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline text-decoration="underline">
                    <xsl:text>Vorauszahlungsdaten</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="AdvanceOriginalInvoiceTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Előlegszámla sorszáma:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Advance payment invoice number:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Vorauszahlungsrechnung:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="AdvancePaymentDateTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Előleg fizetésének dátuma:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Advance payment date:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Datum der Vorauszahlung:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="AdvanceExchangeRateTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Előleg árfolyama:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Exchange rate of advance payment:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Wechselkurs der Vorauszahlung:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ProductCodesData_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Termékkód adatok:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Product codes data:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Daten zu Produktcodes:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="IntermediatedServiceTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>A tétel közvetített szolgáltatást tartalmaz.</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>The item is an intermediated service.</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Der Artikel enthält einen vermittelten Dienst.</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="DepositIndicatorTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>A tétel betétdíj jellegű.</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>The item is of a deposit nature.</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Der Artikel hat Pfandcharakter.</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ObligatedForProductFeeTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>A tételt termékdíj fizetési kötelezettség terheli.</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>The item is liable to product fee.</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Der Artikel unterliegt der Produktgebühr</xsl:text>
                </fo:inline>
                <fo:inline>
                    <xsl:text>.</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="NetaDeclarationTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>A tétel vonatkozásában a Neta törvényben (2011. évi CIII. törvény) meghatározott adófizetési kötelezettség az eladót terheli.</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>In the relation of the item, the seller is liable to pay the tax specified in the Neta Act (Act CIII of 2011).</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>In Bezug auf den Artikel ist der Verkäufer verpflichtet, die im Neta-Gesetz (Gesetz CIII von 2011) festgelegte Steuer zu zahlen.</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="BrandTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Gyártmány / típus:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Brand / type:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Marke / Typ:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="SerialNumTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Alvázszám / gyári szám:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Chassis / serial number:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Fahrgestell / Seriennummer:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="EngineNumTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Motorszám:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Engine number:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Motornummer:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="FirstEntryIntoServiceTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Első forgalomba helyezés időpontja:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>First entry into service:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Erstinbetriebnahme:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="NewTransportMeanTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Új közlekedési eszköz</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>New means of transport</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Neuen Verkehrsmitteln</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
        <fo:inline>
            <xsl:text> - </xsl:text>
        </fo:inline>
        <xsl:choose>
            <xsl:when test="exists(n1:newTransportMean/n1:vehicle)">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>szárazföldi</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>land vehicle</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>landfahrzeug</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="exists(n1:newTransportMean/n1:vessel)">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>vízi</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>watercraft</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Wasserfahrzeug</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="exists(n1:newTransportMean/n1:aircraft)">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>légi</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>aircraft</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Flugzeug</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="EngineCapacityTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Hengerűrtartalom:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Engine capacity:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Motorleistung:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="LengthTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Hossza:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Length:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Länge:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="TakeOffWeightTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Felszállási tömeg:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Take off weight:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Start - Masse:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="KmsTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Futott kilométer:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Kilometers traveled:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Gefahrene Kilometer:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="SailedHoursTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Hajózott órák:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Sailed hours:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Stunden gesegelt:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="OperationHoursTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Repült órák:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Aviated hours:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Geflogene Stunden:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="EnginePowerTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Teljesítmény:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Output:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Leistung:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="NewTransportException">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Áfa törvény 259.§ 25.</xsl:text>
                </fo:inline>
                <xsl:choose>
                    <xsl:when test="n1:newTransportMean/n1:vessel/n1:activityReferred = true()">
                        <fo:inline font-weight="bold">
                            <xsl:text>b</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="n1:newTransportMean/n1:aircraft/n1:airCargo = true()">
                        <fo:inline font-weight="bold">
                            <xsl:text>c</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
                <fo:inline font-weight="bold">
                    <xsl:text>) szerinti kivétel</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Exception under Section 259 (25)</xsl:text>
                </fo:inline>
                <fo:inline>
                    <xsl:text>&#160;</xsl:text>
                </fo:inline>
                <xsl:choose>
                    <xsl:when test="n1:newTransportMean/n1:vessel/n1:activityReferred = true()">
                        <fo:inline font-weight="bold">
                            <xsl:text>b</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="n1:newTransportMean/n1:aircraft/n1:airCargo = true()">
                        <fo:inline font-weight="bold">
                            <xsl:text>c</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
                <fo:inline>
                    <xsl:text>&#160;</xsl:text>
                </fo:inline>
                <fo:inline font-weight="bold">
                    <xsl:text>of the VAT Act</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Ausnahme gemäß § 259 Nr.25 Buchstabe</xsl:text>
                </fo:inline>
                <fo:inline>
                    <xsl:text>&#160;</xsl:text>
                </fo:inline>
                <xsl:choose>
                    <xsl:when test="n1:newTransportMean/n1:vessel/n1:activityReferred = true()">
                        <fo:inline font-weight="bold">
                            <xsl:text>b</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="n1:newTransportMean/n1:aircraft/n1:airCargo = true()">
                        <fo:inline font-weight="bold">
                            <xsl:text>c</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
                <fo:inline font-weight="bold">
                    <xsl:text> UStG.</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="GenericExchangeRateOffset">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:value-of select="translate(replace(format-number(., &apos;##,###,##0.00####&apos;), &apos;,&apos;, &apos; &apos;), &apos;.&apos;, &apos;,&apos;)"/>
                </altova:inline-container-substitute>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:value-of select="format-number(., &apos;##,###,##0.00####&apos;)"/>
                </altova:inline-container-substitute>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:value-of select="translate(translate(replace(format-number(., &apos;##,###,##0.00####&apos;), &apos;,&apos;, &apos; &apos;), &apos;.&apos;, &apos;,&apos;), &apos; &apos;, &apos;.&apos;)"/>
                </altova:inline-container-substitute>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="GPCExciseTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Földgáz, villamos energia, szén jövedéki adója forintban: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Excise duty on natural gas, electricity and coal in HUF: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Verbrauchsteuer auf Erdgas, Strom und Kohle in HUF: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="BaseTaxNumberTemplate">
        <xsl:for-each select="base:taxpayerId">
            <altova:inline-container-substitute font-weight="bold">
                <xsl:apply-templates/>
            </altova:inline-container-substitute>
        </xsl:for-each>
        <xsl:if test="exists(base:vatCode)">
            <fo:inline>
                <xsl:text>-</xsl:text>
            </fo:inline>
            <xsl:for-each select="base:vatCode">
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:apply-templates/>
                </altova:inline-container-substitute>
            </xsl:for-each>
        </xsl:if>
        <xsl:if test="exists(base:countyCode)">
            <fo:inline>
                <xsl:text>-</xsl:text>
            </fo:inline>
            <xsl:for-each select="base:countyCode">
                <altova:inline-container-substitute font-weight="bold">
                    <xsl:apply-templates/>
                </altova:inline-container-substitute>
            </xsl:for-each>
        </xsl:if>
    </xsl:template>
    <xsl:template name="BaseAddressTemplate">
        <xsl:choose>
            <xsl:when test="exists(base:simpleAddress)">
                <xsl:for-each select="base:simpleAddress">
                    <xsl:for-each select="base:countryCode">
                        <altova:inline-container-substitute font-weight="bold">
                            <xsl:apply-templates/>
                        </altova:inline-container-substitute>
                    </xsl:for-each>
                    <xsl:if test="exists(base:region)">
                        <fo:inline font-weight="bold">
                            <xsl:text>&#160;</xsl:text>
                        </fo:inline>
                        <xsl:for-each select="base:region">
                            <altova:inline-container-substitute font-weight="bold">
                                <xsl:apply-templates/>
                            </altova:inline-container-substitute>
                        </xsl:for-each>
                    </xsl:if>
                    <fo:inline font-weight="bold">
                        <xsl:text>&#160;</xsl:text>
                    </fo:inline>
                    <xsl:for-each select="base:postalCode">
                        <altova:inline-container-substitute font-weight="bold">
                            <xsl:apply-templates/>
                        </altova:inline-container-substitute>
                    </xsl:for-each>
                    <fo:inline font-weight="bold">
                        <xsl:text>&#160;</xsl:text>
                    </fo:inline>
                    <xsl:for-each select="base:city">
                        <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                    </xsl:for-each>
                    <fo:inline font-weight="bold">
                        <xsl:text>&#160;</xsl:text>
                    </fo:inline>
                    <xsl:for-each select="base:additionalAddressDetail">
                        <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                    </xsl:for-each>
                </xsl:for-each>
            </xsl:when>
            <xsl:when test="exists(base:detailedAddress)">
                <xsl:for-each select="base:detailedAddress">
                    <xsl:for-each select="base:countryCode">
                        <altova:inline-container-substitute font-weight="bold">
                            <xsl:apply-templates/>
                        </altova:inline-container-substitute>
                    </xsl:for-each>
                    <fo:inline font-weight="bold">
                        <xsl:text>&#160;</xsl:text>
                    </fo:inline>
                    <xsl:if test="exists(base:detailedAddress/base:region)">
                        <xsl:for-each select="base:region">
                            <altova:inline-container-substitute font-weight="bold">
                                <xsl:apply-templates/>
                            </altova:inline-container-substitute>
                        </xsl:for-each>
                        <fo:inline font-weight="bold">
                            <xsl:text>&#160;</xsl:text>
                        </fo:inline>
                    </xsl:if>
                    <xsl:for-each select="base:postalCode">
                        <altova:inline-container-substitute font-weight="bold">
                            <xsl:apply-templates/>
                        </altova:inline-container-substitute>
                    </xsl:for-each>
                    <fo:inline font-weight="bold">
                        <xsl:text>&#160;</xsl:text>
                    </fo:inline>
                    <xsl:for-each select="base:city">
                        <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                    </xsl:for-each>
                    <fo:inline font-weight="bold">
                        <xsl:text>, </xsl:text>
                    </fo:inline>
                    <xsl:for-each select="base:streetName">
                        <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                    </xsl:for-each>
                    <fo:inline font-weight="bold">
                        <xsl:text>&#160;</xsl:text>
                    </fo:inline>
                    <xsl:for-each select="base:publicPlaceCategory">
                        <xsl:call-template name="GenericZeroWidthWhitespaceOffset"/>
                    </xsl:for-each>
                    <xsl:if test="exists(base:number)">
                        <fo:inline font-weight="bold">
                            <xsl:text>&#160;</xsl:text>
                        </fo:inline>
                        <xsl:for-each select="base:number">
                            <altova:inline-container-substitute font-weight="bold">
                                <xsl:apply-templates/>
                            </altova:inline-container-substitute>
                        </xsl:for-each>
                    </xsl:if>
                    <xsl:if test="exists(base:building)">
                        <fo:inline font-weight="bold">
                            <xsl:text>&#160;</xsl:text>
                        </fo:inline>
                        <xsl:for-each select="base:building">
                            <altova:inline-container-substitute font-weight="bold">
                                <xsl:apply-templates/>
                            </altova:inline-container-substitute>
                        </xsl:for-each>
                    </xsl:if>
                    <xsl:if test="exists(base:staircase)">
                        <fo:inline font-weight="bold">
                            <xsl:text>&#160;</xsl:text>
                        </fo:inline>
                        <xsl:for-each select="base:staircase">
                            <altova:inline-container-substitute font-weight="bold">
                                <xsl:apply-templates/>
                            </altova:inline-container-substitute>
                        </xsl:for-each>
                    </xsl:if>
                    <xsl:if test="exists(base:floor)">
                        <fo:inline font-weight="bold">
                            <xsl:text>&#160;</xsl:text>
                        </fo:inline>
                        <xsl:for-each select="base:floor">
                            <altova:inline-container-substitute font-weight="bold">
                                <xsl:apply-templates/>
                            </altova:inline-container-substitute>
                        </xsl:for-each>
                    </xsl:if>
                    <xsl:if test="exists(base:door)">
                        <fo:inline font-weight="bold">
                            <xsl:text>&#160;</xsl:text>
                        </fo:inline>
                        <xsl:for-each select="base:door">
                            <altova:inline-container-substitute font-weight="bold">
                                <xsl:apply-templates/>
                            </altova:inline-container-substitute>
                        </xsl:for-each>
                    </xsl:if>
                    <xsl:if test="exists(base:lotNumber)">
                        <fo:inline font-weight="bold">
                            <xsl:text>&#160;</xsl:text>
                        </fo:inline>
                        <xsl:for-each select="base:lotNumber">
                            <altova:inline-container-substitute font-weight="bold">
                                <xsl:apply-templates/>
                            </altova:inline-container-substitute>
                        </xsl:for-each>
                    </xsl:if>
                    <altova:line-break/>
                </xsl:for-each>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ProductFeeTakeoverTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline text-decoration="underline">
                    <xsl:text>A termékdíj átvállalás adatai</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline text-decoration="underline">
                    <xsl:text>Data of the product fee takeover</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline text-decoration="underline">
                    <xsl:text>Daten der Produktgebührenübernahme</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ProductFeeCustomerDeclarationTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline text-decoration="underline">
                    <xsl:text>A termékdíj mentesülés adatai</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline text-decoration="underline">
                    <xsl:text>Product fee charge exemption details</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline text-decoration="underline">
                    <xsl:text>Einzelheiten zur Produktgebührenbefreiung</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="TakeoverTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Átvállalás iránya és jogszabályi alapja:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Direction and legal base of takeover:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Richtung und Rechtsgrundlage der Übernahme:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ProductStreamTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Termékáram:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Product stream:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Produktstrom:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="TakeoverAmountTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Átvállalt termékdíj összege forintban:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Amount of product fee assumed in HUF:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Summe der übernommenen Produktgebühr im HUF:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ProductFeeWeightTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Termékdíj köteles termék tömege kilogrammban:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Weight of product fee obligated product in kg:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Gewicht des produktgebührenpflichtigen Produkts in kg:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ProductFeeCodeAmount_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>termékdíj összege: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>product fee amount: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Produktgebührenbetrag: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ProductFeeQuantityTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>mennyiség: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>quantity: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Anzahl: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ProductFeeRateTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>díjtétel: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>product fee rate: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Produktgebührensatz: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="DieselOilPurchaseLocationTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Gázolaj beszerzés helye:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Diesel oil purchase location:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Einkaufsort für Dieselöl:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="DieselOilPurchaseTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline text-decoration="underline">
                    <xsl:text>Gázolaj beszerzés adatai</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline text-decoration="underline">
                    <xsl:text>Diesel oil purchase data</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline text-decoration="underline">
                    <xsl:text>Daten zum Kauf von Dieselöl</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="DieselOilPurchaseDateTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Beszerzés dátuma:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Date of purchase:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Kaufdatum:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="DieselOilPurchaseVehicleRegistrationNumberTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Rendszám:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Plate number:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Kennzeichen:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="DieselOilQuantityTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Bérmunka szolgáltatás:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Contract work service:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Auftragsarbeiten:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ProductFeeRateSummaryTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Díjtétel</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Rate unit</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Gebühreneinheit</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ProductFeeAmountSummaryTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Termékdíj összeg</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Product fee amount</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Produktgebühr</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ProductFeeCodeSummaryTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Termékkód</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Product code</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Produktcode</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ProductChargeSumTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Termékdíj összesen: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Product charge sum: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Produktgebührensumme: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ProductFeeOperationTemplate_C5X">
        <xsl:choose>
            <xsl:when test="n1:productFeeOperation = &apos;REFUND&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>A termékdíj összege visszaigénylésre vonatkozik.</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>The amount of the product fee is for a refund.</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Für eine Rückerstattung gilt die Höhe der Produktgebühr.</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="n1:productFeeOperation = &apos;DEPOSIT&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>A termékdíj összege raktárba történő beszállításra vonatkozik.</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>The amount of the product fee applies to delivery to the warehouse.</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Die Höhe der Produktpauschale gilt für die Lieferung an das Lager.</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="PaymentEvidenceDocumentDataTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline text-decoration="underline">
                    <xsl:text>Termékdíj bevallását igazoló dokumentum adatai</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline text-decoration="underline">
                    <xsl:text>Details of the document certifying the product fee declaration</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline text-decoration="underline">
                    <xsl:text>Angaben zum Dokument zur Bescheinigung der Produktgebührenerklärung</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="PaymentEvidenceObligatedTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Kötelezett adószáma, neve: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Obligated name, tax number: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Obligatorischer Name, Steuernummer: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="PaymentEvidenceObligatedAddressTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Kötelezett címe: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Obligor&apos;s address: </xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Adresse des Schuldners: </xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="PaymentEvidenceDocumentNoTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Dokumentum azonosító száma:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Document ID number:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Dokument ID Nummer:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="PaymentEvidenceDocumentDateTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Dokumentum kelte:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Document date:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Datum des Dokuments:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ProductFeeSummaryTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Termékdíj összesítő adatok</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Product fee summary data</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Summierung der Produktgebühren</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="VatTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>ÁFA</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>VAT</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>MwSt</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="InvoiceSummaryInHUFTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Forintban:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>In HUF:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Im HUF:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="InvoiceSummarySumTotalTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Mindösszesen:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Sumtotal:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Gesamtsumme:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="InvoiceSummarySumTotalInHUFTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Forintban:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>In HUF:</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Im HUF:</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ProductFeeMeasuringUnitTemplate_L10N">
        <xsl:choose>
            <xsl:when test=". = &apos;DARAB&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>darab</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>piece</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>stück</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test=". = &apos;KG&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>kg</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="UnitOfMeasureValueTemplate_L10N">
        <xsl:choose>
            <xsl:when test=". = &apos;PIECE&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Darab</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Piece</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Stück</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test=". = &apos;KILOGRAM&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Kilogramm</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Kilogram</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Kilogramm</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test=". = &apos;TON&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Tonna</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Ton</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Tonne</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test=". = &apos;KWH&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Kilowatt óra</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Kilowatt hour</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Kilowattstunde</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test=". = &apos;DAY&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Nap</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Day</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Tag</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test=". = &apos;HOUR&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Óra</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Hour</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Stunde</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test=". = &apos;MINUTE&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Perc</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Minute</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Minute</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test=". = &apos;MONTH&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Hónap</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Month</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Monat</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test=". = &apos;LITER&apos;">
                <fo:inline font-weight="bold">
                    <xsl:text>Liter</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test=". = &apos;KILOMETER&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Kilométer</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Kilometer</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Kilometer</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test=". = &apos;CUBIC_METER&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Köbméter</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Cubic meter</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Kubikmeter</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test=". = &apos;METER&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Méter</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Meter</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Meter</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test=". = &apos;LINEAR_METER&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Folyóméter</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Linear meter</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>laufender Meter</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test=". = &apos;CARTON&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Karton</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Carton</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Karton</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test=". = &apos;PACK&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Csomag</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Pack</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Pack</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ProductStreamTemplate_C5X">
        <xsl:choose>
            <xsl:when test="n1:productStream = &apos;BATTERY&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Akkumulátor</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Battery</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Batterie</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="n1:productStream = &apos;PACKAGING&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Csomagolószer</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Packaging</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Verpackungsmaterial</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="n1:productStream = &apos;OTHER_PETROL&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Egyéb kőolajtermék</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Other petroleum product</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Andere Erdölprodukte</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="n1:productStream = &apos;ELECTRONIC&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Elektromos, elektronikai berendezés</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Electric appliance, electronic equipment</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Elektrische, elektronische Ausrüstung</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="n1:productStream = &apos;TIRE&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Gumiabroncs</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Tire</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Reifen</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="n1:productStream = &apos;COMMERCIAL&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Reklámhordozó papír</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Commercial printing paper</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Werbepapier</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="n1:productStream = &apos;PLASTIC&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Egyéb műanyag termék</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Other plastic product</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Andere Kunstsoffprodukte</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="n1:productStream = &apos;OTHER_CHEMICAL&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Egyéb vegyipari termék</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Other chemical product</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Andere chemische Produkte</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
            <xsl:when test="n1:productStream = &apos;PAPER&apos;">
                <xsl:choose>
                    <xsl:when test="$lang = &apos;HU&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Irodai papír</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;EN&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Paper stationery</xsl:text>
                        </fo:inline>
                    </xsl:when>
                    <xsl:when test="$lang = &apos;DE&apos;">
                        <fo:inline font-weight="bold">
                            <xsl:text>Büropapier</xsl:text>
                        </fo:inline>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="ProductFeeSummaryQuantityTemplate_L10N">
        <xsl:choose>
            <xsl:when test="$lang = &apos;HU&apos;">
                <fo:inline>
                    <xsl:text>Mennyiség</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;EN&apos;">
                <fo:inline>
                    <xsl:text>Quantity</xsl:text>
                </fo:inline>
            </xsl:when>
            <xsl:when test="$lang = &apos;DE&apos;">
                <fo:inline>
                    <xsl:text>Menge</xsl:text>
                </fo:inline>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="altova:double-backslash">
        <xsl:param name="text"/>
        <xsl:param name="text-length"/>
        <xsl:variable name="text-after-bs" select="substring-after($text, '\')"/>
        <xsl:variable name="text-after-bs-length" select="string-length($text-after-bs)"/>
        <xsl:choose>
            <xsl:when test="$text-after-bs-length = 0">
                <xsl:choose>
                    <xsl:when test="substring($text, $text-length) = '\'">
                        <xsl:value-of select="concat(substring($text,1,$text-length - 1), '\\')"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$text"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="concat(substring($text,1,$text-length - $text-after-bs-length - 1), '\\')"/>
                <xsl:call-template name="altova:double-backslash">
                    <xsl:with-param name="text" select="$text-after-bs"/>
                    <xsl:with-param name="text-length" select="$text-after-bs-length"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="altova:MakeValueAbsoluteIfPixels">
        <xsl:param name="sValue"/>
        <xsl:variable name="sBeforePx" select="substring-before($sValue, 'px')"/>
        <xsl:choose>
            <xsl:when test="$sBeforePx">
                <xsl:variable name="nLengthOfInteger">
                    <xsl:call-template name="altova:GetCharCountOfIntegerAtEndOfString">
                        <xsl:with-param name="sText" select="$sBeforePx"/>
                    </xsl:call-template>
                </xsl:variable>
                <xsl:variable name="nPosOfInteger" select="string-length($sBeforePx) - $nLengthOfInteger + 1"/>
                <xsl:variable name="nValuePx" select="substring($sBeforePx, $nPosOfInteger)"/>
                <xsl:variable name="nValueIn" select="number($nValuePx) div number($altova:nPxPerIn)"/>
                <xsl:variable name="nLengthBeforeInteger" select="string-length($sBeforePx) - $nLengthOfInteger"/>
                <xsl:variable name="sRest">
                    <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                        <xsl:with-param name="sValue" select="substring-after($sValue, 'px')"/>
                    </xsl:call-template>
                </xsl:variable>
                <xsl:value-of select="concat(substring($sBeforePx, 1, $nLengthBeforeInteger), string($nValueIn), 'in', $sRest)"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$sValue"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template name="altova:GetCharCountOfIntegerAtEndOfString">
        <xsl:param name="sText"/>
        <xsl:variable name="sLen" select="string-length($sText)"/>
        <xsl:variable name="cLast" select="substring($sText, $sLen)"/>
        <xsl:choose>
            <xsl:when test="number($cLast) &gt;= 0 and number($cLast) &lt;= 9">
                <xsl:variable name="nResultOfRest">
                    <xsl:call-template name="altova:GetCharCountOfIntegerAtEndOfString">
                        <xsl:with-param name="sText" select="substring($sText, 1, $sLen - 1)"/>
                    </xsl:call-template>
                </xsl:variable>
                <xsl:value-of select="$nResultOfRest + 1"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:text>0</xsl:text>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="@* | node()" mode="altova:copy-table">
        <xsl:copy>
            <xsl:apply-templates select="@* | node()" mode="#current"/>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="fo:table" mode="altova:copy-table">
        <xsl:choose>
            <xsl:when test="(empty(fo:table-body/fo:table-row) and empty(fo:table-header/fo:table-row) and empty(fo:table-footer/fo:table-row)) or empty(.//fo:table-cell)">                </xsl:when>
            <xsl:otherwise>
                <xsl:copy>
                    <xsl:apply-templates select="@* | node()" mode="#current"/>
                </xsl:copy>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="fo:table-header | fo:table-footer" mode="altova:copy-table">
        <xsl:choose>
            <xsl:when test="empty(fo:table-row)">               </xsl:when>
            <xsl:otherwise>
                <xsl:copy>
                    <xsl:apply-templates select="@* | node()" mode="#current"/>
                </xsl:copy>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="fo:table-body" mode="altova:copy-table">
        <xsl:choose>
            <xsl:when test="empty(fo:table-row)">
                <xsl:copy>
                    <xsl:apply-templates select="@* | node()" mode="#current"/>
                    <fo:table-row>
                        <fo:table-cell>
                            <fo:block/>
                        </fo:table-cell>
                    </fo:table-row>
                </xsl:copy>
            </xsl:when>
            <xsl:otherwise>
                <xsl:copy-of select="."/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="fo:table-row" mode="altova:copy-table">
        <xsl:choose>
            <xsl:when test="empty(fo:table-cell)">
                <xsl:copy>
                    <fo:table-cell>
                        <fo:block/>
                    </fo:table-cell>
                </xsl:copy>
            </xsl:when>
            <xsl:otherwise>
                <xsl:copy-of select="."/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="fo:list-item[empty(@break-before)]" mode="second-step">
        <xsl:copy>
            <xsl:choose>
                <xsl:when test=".//altova:page-break">
                    <xsl:attribute name="break-before" select="'page'"/>
                </xsl:when>
                <xsl:when test=".//altova:column-break">
                    <xsl:attribute name="break-before" select="'column'"/>
                </xsl:when>
            </xsl:choose>
            <xsl:apply-templates select="@* | node()" mode="#current"/>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="altova:pdf-bookmark-tree[@generate-from-toc = false()]" mode="second-step">
        <xsl:variable name="altova:pdf-bookmarks">
            <xsl:apply-templates mode="altova:filter-pdf-bookmark-tree"/>
        </xsl:variable>
        <xsl:if test="exists($altova:pdf-bookmarks/fo:bookmark)">
            <fo:bookmark-tree>
                <xsl:copy-of select="$altova:pdf-bookmarks"/>
            </fo:bookmark-tree>
        </xsl:if>
    </xsl:template>
    <xsl:template match="node()" mode="altova:filter-pdf-bookmark-tree">
        <xsl:apply-templates mode="#current"/>
    </xsl:template>
    <xsl:template match="altova:pdf-bookmark" mode="altova:filter-pdf-bookmark-tree">
        <fo:bookmark>
            <xsl:sequence select="@starting-state"/>
            <xsl:variable name="altova:hyperlink" select="altova:hyperlink[1]"/>
            <xsl:choose>
                <xsl:when test="not(exists($altova:hyperlink))">
                    <xsl:message select="'Error: Found PDF bookmark without a corresponding hyperlink!'" terminate="yes"/>
                    <xsl:attribute name="internal-destination" select="'undefined'"/>
                    <fo:bookmark-title/>
                </xsl:when>
                <xsl:when test="not(starts-with($altova:hyperlink/@href,'#'))">
                    <xsl:message select="concat('Error: External bookmark destinations (',$altova:hyperlink/@href,') are not supported by FOP 0.93/1.0!')" terminate="yes"/>
                    <xsl:attribute name="internal-destination" select="'external-destination-not-supported'"/>
                    <fo:bookmark-title>
                        <xsl:sequence select="$altova:hyperlink//@color[1] | $altova:hyperlink//@font-style[.='normal' or .='italic'][1] | $altova:hyperlink//@font-weight[.='normal' or .='bold'][1]"/>
                        <xsl:value-of select="string($altova:hyperlink)"/>
                    </fo:bookmark-title>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:variable name="altova:bookmark-name" select="substring($altova:hyperlink/@href,2)"/>
                    <xsl:variable name="altova:source-document" select="ancestor::*[self::altova:result-document | self::altova:main-document][1]"/>
                    <xsl:variable name="altova:target-document" select="key('altova:bookmark-key',$altova:bookmark-name,$altova:design-xslt-tree-view)[1]/ancestor::*[self::altova:result-document | self::altova:main-document][1]"/>
                    <xsl:choose>
                        <xsl:when test="empty($altova:target-document) or $altova:source-document is $altova:target-document">
                            <xsl:attribute name="internal-destination" select="$altova:bookmark-name"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:variable name="altova:relative-path" select="altova:calc-relative-path($altova:source-document/@url,$altova:target-document/@url)"/>
                            <xsl:variable name="altova:url" select="concat(substring($altova:relative-path,1,string-length($altova:relative-path) - 3),'.pdf#dest=',$altova:bookmark-name)"/>
                            <xsl:message select="concat('Warning: External bookmark destinations (',$altova:url,') are not supported by FOP 0.93/1.0!')"/>
                            <xsl:attribute name="internal-destination" select="'external-destination-not-supported'"/>
                        </xsl:otherwise>
                    </xsl:choose>
                    <fo:bookmark-title>
                        <xsl:sequence select="$altova:hyperlink//@color[1] | $altova:hyperlink//@font-style[.='normal' or .='italic'][1] | $altova:hyperlink//@font-weight[.='normal' or .='bold'][1]"/>
                        <xsl:value-of select="string($altova:hyperlink)"/>
                    </fo:bookmark-title>
                </xsl:otherwise>
            </xsl:choose>
            <xsl:if test="count(altova:hyperlink) gt 1">
                <xsl:message select="concat('Error: Found PDF bookmark (',$altova:hyperlink/@href,') with more than one corresponding hyperlinks!')" terminate="yes"/>
            </xsl:if>
            <xsl:apply-templates mode="#current"/>
        </fo:bookmark>
    </xsl:template>
    <xsl:template match="fo:basic-link" mode="second-step">
        <xsl:choose>
            <xsl:when test="count(node()) eq 1 and fo:block">
                <fo:block>
                    <xsl:apply-templates select="fo:block[1]/@*" mode="#current"/>
                    <fo:basic-link>
                        <xsl:apply-templates select="@*" mode="#current"/>
                        <xsl:apply-templates select="fo:block[1]/node()" mode="#current"/>
                    </fo:basic-link>
                </fo:block>
            </xsl:when>
            <xsl:otherwise>
                <xsl:copy>
                    <xsl:apply-templates select="@* | node()" mode="#current"/>
                </xsl:copy>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:function name="altova:is-node-empty" as="xs:boolean">
        <xsl:param name="altova:node" as="element()"/>
        <xsl:sequence select="every $altova:child in $altova:node/child::node() satisfies ( ( boolean( $altova:child/self::text() ) and string-length( $altova:child ) = 0 ) or ( ( boolean( $altova:child/self::altova:inline-container-substitute ) or boolean( $altova:child/self::fo:inline ) or boolean( $altova:child/self::fo:inline-container ) or boolean( $altova:child/self::fo:block-container ) or boolean( $altova:child/self::fo:block ) or boolean( $altova:child/self::fo:basic-link ) ) and altova:is-node-empty( $altova:child ) ) )"/>
    </xsl:function>
    <xsl:function name="altova:col-span" as="xs:integer">
        <xsl:param name="altova:cell" as="element()"/>
        <xsl:sequence select="if ( exists( $altova:cell/@number-columns-spanned ) ) then xs:integer( $altova:cell/@number-columns-spanned ) else 1"/>
    </xsl:function>
    <xsl:function name="altova:is-cell-empty" as="xs:boolean">
        <xsl:param name="altova:cell" as="element()"/>
        <xsl:sequence select="altova:is-node-empty( $altova:cell )"/>
    </xsl:function>
    <xsl:template match="@* | node()" mode="altova:generate-table">
        <xsl:param name="altova:generate-cols"/>
        <xsl:param name="altova:TableIndexInfo"/>
        <xsl:copy>
            <xsl:apply-templates select="@* | node()" mode="#current">
                <xsl:with-param name="altova:generate-cols" select="$altova:generate-cols"/>
                <xsl:with-param name="altova:TableIndexInfo" select="$altova:TableIndexInfo"/>
            </xsl:apply-templates>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="@altova:hide-rows | @altova:hide-cols | @altova:is-body-cell" mode="altova:generate-table"/>
    <xsl:template match="fo:table-row" mode="altova:generate-table">
        <xsl:param name="altova:generate-cols"/>
        <xsl:param name="altova:TableIndexInfo"/>
        <xsl:choose>
            <xsl:when test="ancestor::fo:table[ 1 ]/@altova:hide-rows = 'empty'">
                <xsl:if test="some $altova:cell in fo:table-cell satisfies not( altova:is-cell-empty( $altova:cell ) )">
                    <xsl:copy>
                        <xsl:apply-templates select="@* | node()" mode="#current">
                            <xsl:with-param name="altova:generate-cols" select="$altova:generate-cols"/>
                            <xsl:with-param name="altova:TableIndexInfo" select="$altova:TableIndexInfo"/>
                        </xsl:apply-templates>
                    </xsl:copy>
                </xsl:if>
            </xsl:when>
            <xsl:when test="ancestor::fo:table[ 1 ]/@altova:hide-rows = 'body-empty'">
                <xsl:if test="not( exists( parent::fo:table-body ) ) or ( some $altova:cell in fo:table-cell[ @altova:is-body-cell = 'true' ] satisfies not( altova:is-cell-empty( $altova:cell ) ) )">
                    <xsl:copy>
                        <xsl:apply-templates select="@* | node()" mode="#current">
                            <xsl:with-param name="altova:generate-cols" select="$altova:generate-cols"/>
                            <xsl:with-param name="altova:TableIndexInfo" select="$altova:TableIndexInfo"/>
                        </xsl:apply-templates>
                    </xsl:copy>
                </xsl:if>
            </xsl:when>
            <xsl:otherwise>
                <xsl:copy>
                    <xsl:apply-templates select="@* | node()" mode="#current">
                        <xsl:with-param name="altova:generate-cols" select="$altova:generate-cols"/>
                        <xsl:with-param name="altova:TableIndexInfo" select="$altova:TableIndexInfo"/>
                    </xsl:apply-templates>
                </xsl:copy>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:function name="altova:col-position" as="xs:integer">
        <xsl:param name="altova:Cell" as="element()"/>
        <xsl:param name="altova:TableIndexInfo" as="element()"/>
        <xsl:variable name="altova:nRow" select="altova:GetGridRowNumForCell($altova:Cell)"/>
        <xsl:variable name="altova:nCell" select="count($altova:Cell/preceding-sibling::fo:table-cell) + 1" as="xs:integer"/>
        <xsl:sequence select="$altova:TableIndexInfo/altova:Row[$altova:nRow]/altova:ColumnIndex[$altova:nCell]"/>
    </xsl:function>
    <xsl:template match="fo:table-cell" mode="altova:generate-table">
        <xsl:param name="altova:generate-cols"/>
        <xsl:param name="altova:TableIndexInfo"/>
        <xsl:variable name="altova:this-cell" select="."/>
        <xsl:variable name="altova:col-index" select="altova:col-position($altova:this-cell, $altova:TableIndexInfo)"/>
        <xsl:choose>
            <xsl:when test="$altova:generate-cols[ $altova:col-index ]">
                <xsl:copy>
                    <xsl:apply-templates select="@*" mode="#current">
                        <xsl:with-param name="altova:generate-cols" select="$altova:generate-cols"/>
                        <xsl:with-param name="altova:TableIndexInfo" select="$altova:TableIndexInfo"/>
                    </xsl:apply-templates>
                    <xsl:copy-of select="node()"/>
                </xsl:copy>
            </xsl:when>
            <xsl:when test="altova:col-span( $altova:this-cell ) > 1">
                <xsl:for-each select="for $altova:pos in $altova:col-index to ( $altova:col-index + altova:col-span( $altova:this-cell ) - 1 ) return if ( $altova:generate-cols[ $altova:pos ] ) then true() else ()">
                    <fo:table-cell>
                        <xsl:apply-templates select="$altova:this-cell/@*" mode="altova:copy-table-cell-properties"/>
                        <fo:block/>
                    </fo:table-cell>
                </xsl:for-each>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="fo:table-column" mode="altova:generate-table">
        <xsl:param name="altova:generate-cols"/>
        <xsl:param name="altova:TableIndexInfo"/>
        <xsl:variable name="altova:col-index" select="count( preceding-sibling::fo:table-column ) + 1"/>
        <xsl:if test="$altova:generate-cols[ $altova:col-index ]">
            <xsl:copy>
                <xsl:apply-templates select="@*" mode="#current">
                    <xsl:with-param name="altova:generate-cols" select="$altova:generate-cols"/>
                    <xsl:with-param name="altova:TableIndexInfo" select="$altova:TableIndexInfo"/>
                </xsl:apply-templates>
                <xsl:copy-of select="node()"/>
            </xsl:copy>
        </xsl:if>
    </xsl:template>
    <xsl:template match="@number-columns-spanned" mode="altova:generate-table">
        <xsl:param name="altova:generate-cols"/>
        <xsl:param name="altova:TableIndexInfo"/>
        <xsl:choose>
            <xsl:when test="exists( ancestor::fo:table[ 1 ]/@altova:hide-cols )">
                <xsl:variable name="altova:col-index" select="altova:col-position(.., $altova:TableIndexInfo)"/>
                <xsl:attribute name="number-columns-spanned" select="sum( for $altova:pos in $altova:col-index to ( $altova:col-index + xs:integer( . ) - 1 ) return if ( $altova:generate-cols[ $altova:pos ] ) then 1 else 0 )"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:attribute name="number-columns-spanned" select="."/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="@*" mode="altova:copy-table-cell-properties">
        <xsl:copy-of select="."/>
    </xsl:template>
    <xsl:template match="@altova:is-body-cell" mode="altova:copy-table-cell-properties"/>
    <xsl:template match="@number-columns-spanned" mode="altova:copy-table-cell-properties"/>
    <xsl:template match="/">
        <xsl:apply-templates select="$altova:design-xslt-tree-view" mode="second-step"/>
    </xsl:template>
    <xsl:template match="altova:result-document" mode="second-step">
        <xsl:result-document href="{@url}">
            <xsl:apply-templates mode="#current"/>
        </xsl:result-document>
    </xsl:template>
    <xsl:template match="altova:bookmark" mode="second-step">
        <xsl:variable name="bookmark-content">
            <altova:bookmark>
                <xsl:apply-templates select="node()" mode="#current"/>
            </altova:bookmark>
        </xsl:variable>
        <xsl:choose>
            <xsl:when test="altova:is-node-empty($bookmark-content/altova:bookmark)">
                <fo:block>
                    <xsl:apply-templates select="@*" mode="#current"/>
                    <xsl:copy-of select="$bookmark-content/altova:bookmark/node()"/>
                </fo:block>
            </xsl:when>
            <xsl:otherwise>
                <fo:inline>
                    <xsl:apply-templates select="@*" mode="#current"/>
                    <xsl:copy-of select="$bookmark-content/altova:bookmark/node()"/>
                </fo:inline>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:key name="altova:bookmark-key" match="altova:bookmark" use="@name"/>
    <xsl:template match="altova:bookmark/@name" mode="second-step">
        <xsl:attribute name="id" select="."/>
    </xsl:template>
    <xsl:template match="altova:hyperlink" mode="second-step">
        <fo:basic-link>
            <xsl:apply-templates select="@* | node()" mode="#current"/>
        </fo:basic-link>
    </xsl:template>
    <xsl:template match="altova:hyperlink/@href" mode="second-step">
        <xsl:choose>
            <xsl:when test="not(string(.))">
                <xsl:attribute name="external-destination" select="'url()'"/>
            </xsl:when>
            <xsl:when test="starts-with(.,'#')">
                <xsl:variable name="altova:bookmark-name" select="substring(.,2)"/>
                <xsl:variable name="altova:source-document" select="ancestor::*[self::altova:result-document | self::altova:main-document][1]"/>
                <xsl:variable name="altova:target-document" select="key('altova:bookmark-key',$altova:bookmark-name,$altova:design-xslt-tree-view)[1]/ancestor::*[self::altova:result-document | self::altova:main-document][1]"/>
                <xsl:choose>
                    <xsl:when test="empty($altova:target-document) or $altova:source-document is $altova:target-document">
                        <xsl:attribute name="internal-destination" select="$altova:bookmark-name"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:variable name="altova:relative-path" select="altova:calc-relative-path($altova:source-document/@url,$altova:target-document/@url)"/>
                        <xsl:variable name="altova:url" select="concat(substring($altova:relative-path,1,string-length($altova:relative-path) - 3),'.pdf#dest=',$altova:bookmark-name)"/>
                        <xsl:variable name="altova:escaped-url">
                            <xsl:call-template name="altova:double-backslash">
                                <xsl:with-param name="text" select="$altova:url"/>
                                <xsl:with-param name="text-length" select="string-length($altova:url)"/>
                            </xsl:call-template>
                        </xsl:variable>
                        <xsl:attribute name="external-destination" select="concat('url(',$altova:escaped-url,')')"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:variable name="altova:escaped-url">
                    <xsl:choose>
                        <xsl:when test="starts-with(.,'\\') or substring(.,2,1)=':'">
                            <xsl:value-of select="iri-to-uri(concat('file:///',translate(.,'\','/')))"/>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:value-of select="iri-to-uri(.)"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:variable>
                <xsl:attribute name="external-destination" select="concat('url(',$altova:escaped-url,')')"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="altova:inline-container-substitute" mode="second-step">
        <xsl:choose>
            <xsl:when test="altova:IsInline(.)">
                <fo:inline>
                    <xsl:copy-of select="@* except @altova-DisableOutputEscaping"/>
                    <xsl:apply-templates mode="second-step"/>
                </fo:inline>
            </xsl:when>
            <xsl:otherwise>
                <fo:block>
                    <xsl:copy-of select="@* except @altova-DisableOutputEscaping"/>
                    <xsl:apply-templates mode="second-step"/>
                </fo:block>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:function name="altova:IsInline" as="xs:boolean">
        <xsl:param name="nodePassed" as="node()"/>
        <xsl:choose>
            <xsl:when test="(empty(for $i in $nodePassed/child::node() return index-of(('fo:block', 'fo:block-container', 'fo:list-block', 'fo:table'), name($i))) and (every $i in $nodePassed/altova:inline-container-substitute satisfies altova:IsInline($i))) or name($nodePassed/../..) eq 'fo:list-item-body'">
                <xsl:sequence select="true()"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:sequence select="false()"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:function>
    <xsl:template match="altova:main-document" mode="second-step">
        <xsl:apply-templates mode="#current"/>
    </xsl:template>
    <xsl:template match="altova:basic-link-container" mode="second-step">
        <fo:block text-align-last="justify">
            <xsl:apply-templates mode="second-step"/>
        </fo:block>
    </xsl:template>
    <xsl:template match="altova:page-break" mode="second-step">
        <xsl:if test="empty(ancestor::fo:list-item)">
            <fo:block break-after="page"/>
        </xsl:if>
    </xsl:template>
    <xsl:template match="altova:line-break" mode="second-step">
        <xsl:variable name="name-following-sibling" select="name(following-sibling::*[1])"/>
        <xsl:variable name="name-preceding-sibling" select="name(preceding-sibling::*[1])"/>
        <xsl:if test="($name-following-sibling and $name-following-sibling != 'fo:table' and $name-following-sibling != 'altova:line-break' and $name-preceding-sibling != 'altova:basic-link-container') or $name-preceding-sibling = 'altova:line-break'">
            <fo:block>
                <xsl:if test="not($name-preceding-sibling = ('altova:inline-container-substitute', 'fo:inline', 'fo:basic-link', 'altova:bookmark', 'altova:hyperlink', 'altova:user-defined-element')) or not($name-following-sibling = ('altova:inline-container-substitute', 'fo:inline', 'fo:basic-link', 'altova:bookmark', 'altova:hyperlink', 'altova:user-defined-element'))">
                    <fo:leader leader-pattern="space"/>
                </xsl:if>
            </fo:block>
        </xsl:if>
    </xsl:template>
    <xsl:template match="altova:column-break" mode="second-step">
        <xsl:if test="empty(ancestor::fo:list-item)">
            <fo:block break-after="column"/>
        </xsl:if>
    </xsl:template>
    <xsl:template match="altova:user-defined-element" mode="second-step">
        <xsl:apply-templates mode="second-step"/>
    </xsl:template>
    <xsl:function name="altova:calc-relative-path" as="xs:string">
        <xsl:param name="altova:source-path"/>
        <xsl:param name="altova:target-path"/>
        <xsl:choose>
            <xsl:when test="$altova:source-path = $altova:target-path">
                <xsl:sequence select="''"/>
            </xsl:when>
            <xsl:when test="starts-with($altova:target-path,'http:') or starts-with($altova:target-path,'ftp:') or starts-with($altova:target-path,'file:')">
                <xsl:sequence select="$altova:target-path"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:variable name="altova:source-parts" select="tokenize($altova:source-path,'[/\\]')"/>
                <xsl:variable name="altova:target-parts" select="tokenize($altova:target-path,'[/\\]')"/>
                <xsl:variable name="altova:common-part-count" select="sum(for $i in (1 to count($altova:source-parts) - 1) return if( subsequence($altova:source-parts,1,$i) = subsequence($altova:target-parts,1,$i) ) then 1 else 0)"/>
                <xsl:sequence select="string-join((for $i in ($altova:common-part-count + 1 to count($altova:source-parts) - 1) return '..',for $i in ($altova:common-part-count + 1 to count($altova:target-parts)) return $altova:target-parts[$i]),'/')"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:function>
    <xsl:function name="altova:map-preview-filepath" as="xs:string">
        <xsl:param name="filepath"/>
        <xsl:sequence select="altovaext:create-temp-file-mapping($filepath)" use-when="function-available('altovaext:create-temp-file-mapping',1)"/>
        <xsl:sequence select="$filepath" use-when="not(function-available('altovaext:create-temp-file-mapping',1))"/>
    </xsl:function>
    <xsl:template match="*|@*|comment()|processing-instruction()" mode="second-step">
        <xsl:copy>
            <xsl:apply-templates select="node()|@* except @altova-DisableOutputEscaping" mode="second-step"/>
        </xsl:copy>
    </xsl:template>
    <xsl:template match="text()" mode="second-step">
        <xsl:choose>
            <xsl:when test="../@altova-DisableOutputEscaping">
                <xsl:value-of select="." disable-output-escaping="yes"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:copy/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:template match="altova:number" mode="second-step">
        <xsl:number level="single" count="altova:dummy-list-item | fo:list-item" format="{@format}" from="fo:list-block"/>
    </xsl:template>
    <xsl:template match="altova:dummy-list-item" mode="second-step"/>
    <xsl:template match="altova:pdf-file-attach" mode="second-step"/>
    <xsl:function name="altova:AddCompositeStyles">
        <xsl:param name="altova:sStyleList" as="xs:string?"/>
        <xsl:variable name="altova:seqStyles" select="tokenize($altova:sStyleList, ';')" as="xs:string*"/>
        <xsl:for-each select="$altova:seqStyles">
            <xsl:variable name="altova:sStyleName" select="normalize-space(substring-before(., ':'))" as="xs:string?"/>
            <xsl:variable name="altova:sStyleValue" as="xs:string?">
                <xsl:call-template name="altova:MakeValueAbsoluteIfPixels">
                    <xsl:with-param name="sValue" select="normalize-space(substring-after(., ':'))"/>
                </xsl:call-template>
            </xsl:variable>
            <xsl:if test="$altova:sStyleName and $altova:sStyleValue">
                <xsl:attribute name="{$altova:sStyleName}" select="$altova:sStyleValue"/>
            </xsl:if>
        </xsl:for-each>
    </xsl:function>
    <xsl:function name="altova:AddDynamicClassStyles">
        <xsl:param name="altova:sClassName" as="xs:string?"/>
        <xsl:variable name="nodeRule" select="$altova:nodeCssClasses/Class[@sSelector eq $altova:sClassName and ($altova:sCssSwitch eq '' or @sFile eq $altova:sCssSwitch)]" as="node()*"/>
        <xsl:if test="$nodeRule">
            <xsl:copy-of select="$nodeRule/Styles/@*"/>
        </xsl:if>
    </xsl:function>
    <xsl:function name="altova:GetCellFromRow" as="node()?" xpath-default-namespace="">
        <xsl:param name="nodeTableRow" as="node()"/>
        <xsl:param name="nCell" as="xs:integer"/>
        <xsl:sequence select="$nodeTableRow/(fo:table-cell | fo:table-cell)[$nCell]"/>
    </xsl:function>
    <xsl:function name="altova:GetCellsFromRow" as="node()*" xpath-default-namespace="">
        <xsl:param name="nodeTableRow" as="node()"/>
        <xsl:sequence select="$nodeTableRow/(fo:table-cell | fo:table-cell)"/>
    </xsl:function>
    <xsl:function name="altova:GetRowsFromTable" as="node()*" xpath-default-namespace="">
        <xsl:param name="nodeTable" as="node()"/>
        <xsl:sequence select="$nodeTable/(fo:table-header | fo:table-body | fo:table-footer)/fo:table-row"/>
    </xsl:function>
    <xsl:function name="altova:GetRowspanFromCell" as="xs:integer" xpath-default-namespace="">
        <xsl:param name="altova:nodeCell" as="node()"/>
        <xsl:variable name="altova:sRowSpan" select="$altova:nodeCell/@number-rows-spanned" as="xs:string?"/>
        <xsl:variable name="altova:nRowSpan" select="if ($altova:sRowSpan) then xs:integer($altova:sRowSpan) else 1" as="xs:integer"/>
        <xsl:sequence select="$altova:nRowSpan"/>
    </xsl:function>
    <!-- In HTML and FO, the table section order is header, footer, body. This function determines, for a given cell, the number its row would have if the section order were header, body, footer -->
    <xsl:function name="altova:GetGridRowNumForCell" xpath-default-namespace="">
        <xsl:param name="altova:nodeTableCell" as="node()"/>
        <xsl:variable name="altova:nodeTableRow" select="$altova:nodeTableCell/.." as="node()"/>
        <xsl:variable name="altova:nodeTableSection" select="$altova:nodeTableRow/.." as="node()"/>
        <xsl:variable name="altova:sTableSection" select="fn:local-name($altova:nodeTableSection)" as="xs:string"/>
        <xsl:variable name="altova:nodeTable" select="$altova:nodeTableSection/.." as="node()"/>
        <xsl:variable name="altova:nRowNumInSection" select="count($altova:nodeTableRow/preceding-sibling::fo:table-row) + 1" as="xs:integer"/>
        <xsl:choose>
            <xsl:when test="$altova:sTableSection eq 'table-body'">
                <xsl:sequence select="count($altova:nodeTable/fo:table-header/fo:table-row) + $altova:nRowNumInSection"/>
            </xsl:when>
            <xsl:when test="$altova:sTableSection eq 'table-header'">
                <xsl:sequence select="$altova:nRowNumInSection"/>
            </xsl:when>
            <xsl:when test="$altova:sTableSection eq 'table-footer'">
                <xsl:sequence select="count($altova:nodeTable/fo:table-header/fo:table-row) + count($altova:nodeTable/fo:table-body/fo:table-row) + $altova:nRowNumInSection"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:message select="'Internal Error'" terminate="yes"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:function>
    <xsl:function name="altova:BuildTableIndexInfo" as="node()" xpath-default-namespace="">
        <xsl:param name="altova:nodeTable" as="node()"/>
        <altova:ColumnIndices>
            <xsl:variable name="altova:nodeAllRows" select="altova:GetRowsFromTable($altova:nodeTable/fo:table)" as="node()*"/>
            <xsl:if test="$altova:nodeAllRows">
                <xsl:variable name="altova:seqCellsInFirstRow" select="altova:GetCellsFromRow($altova:nodeAllRows[1])" as="node()*"/>
                <xsl:variable name="altova:nColumnsInTable" select="sum(for $nodeCell in $altova:seqCellsInFirstRow return altova:col-span($nodeCell))" as="xs:integer"/>
                <xsl:variable name="altova:seqActiveRowSpans" select="for $Cell in 1 to $altova:nColumnsInTable return 0" as="xs:integer*"/>
                <!--xsl:sequence select="altova:BuildTableIndexInfo_Recursive($altova:nodeAllRows, 1, $altova:seqActiveRowSpans)"/-->
                <xsl:call-template name="altova:BuildTableIndexInfo_Recursive">
                    <xsl:with-param name="altova:nodeTableRows" select="$altova:nodeAllRows"/>
                    <xsl:with-param name="altova:nRow" select="1"/>
                    <xsl:with-param name="altova:seqActiveRowSpans" select="$altova:seqActiveRowSpans"/>
                </xsl:call-template>
            </xsl:if>
        </altova:ColumnIndices>
    </xsl:function>
    <!--xsl:template name="altova:BuildTableIndexInfo_Recursive" as="node()*" xpath-default-namespace=""-->
    <xsl:template name="altova:BuildTableIndexInfo_Recursive" xpath-default-namespace="">
        <xsl:param name="altova:nodeTableRows" as="node()*"/>
        <xsl:param name="altova:nRow" as="xs:integer"/>
        <xsl:param name="altova:seqActiveRowSpans" as="xs:integer*"/>
        <xsl:variable name="altova:nodeRow" select="$altova:nodeTableRows[$altova:nRow]" as="node()?"/>
        <xsl:choose>
            <xsl:when test="empty($altova:nodeRow)">
                <xsl:sequence select="()"/>
            </xsl:when>
            <xsl:when test="empty(altova:GetCellFromRow($altova:nodeRow, 1))">
                <xsl:sequence select="()"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:variable name="altova:nodeCell1" select="altova:GetCellFromRow($altova:nodeRow, 1)" as="node()?"/>
                <xsl:variable name="altova:nColSpan" select="altova:col-span($altova:nodeCell1)" as="xs:integer"/>
                <altova:Row>
                    <!--xsl:variable name="altova:seqColumnIndicesOfCurrentRow" select="altova:BuildTableIndexInfo_SingleRow_Recursive($altova:seqActiveRowSpans, $altova:nodeRow, 1, 1, $altova:nColSpan, true(), 0, 0)" as="node()*"/>
                        <xsl:sequence select="$altova:seqColumnIndicesOfCurrentRow"/-->
                    <xsl:call-template name="altova:BuildTableIndexInfo_SingleRow_Recursive">
                        <xsl:with-param name="altova:seqActiveRowSpans" select="$altova:seqActiveRowSpans"/>
                        <xsl:with-param name="altova:nodeRow" select="$altova:nodeRow"/>
                        <xsl:with-param name="altova:nColumn" select="1"/>
                        <xsl:with-param name="altova:nCellInCurrentRow" select="1"/>
                        <xsl:with-param name="altova:nColSpanInCellRemaining" select="$altova:nColSpan"/>
                        <xsl:with-param name="altova:bColSpanBegins" select="true()"/>
                        <xsl:with-param name="altova:nCurrentSum" select="0"/>
                        <xsl:with-param name="altova:nRowSpansToAdd" select="0"/>
                    </xsl:call-template>
                </altova:Row>
                <xsl:variable name="altova:seqActiveRowSpans_New" select="altova:BuildTableIndexInfo_ActiveRowSpans_Recursive($altova:seqActiveRowSpans, $altova:nodeRow, 1, 1, $altova:nColSpan)" as="xs:integer*"/>
                <!--xsl:sequence select="altova:BuildTableIndexInfo_Recursive($altova:nodeTableRows, $altova:nRow + 1, $altova:seqActiveRowSpans_New)"/-->
                <xsl:call-template name="altova:BuildTableIndexInfo_Recursive">
                    <xsl:with-param name="altova:nodeTableRows" select="$altova:nodeTableRows"/>
                    <xsl:with-param name="altova:nRow" select="$altova:nRow + 1"/>
                    <xsl:with-param name="altova:seqActiveRowSpans" select="$altova:seqActiveRowSpans_New"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <!--xsl:template name="altova:BuildTableIndexInfo_SingleRow_Recursive" as="node()*" xpath-default-namespace=""-->
    <xsl:template name="altova:BuildTableIndexInfo_SingleRow_Recursive" xpath-default-namespace="">
        <xsl:param name="altova:seqActiveRowSpans" as="xs:integer*"/>
        <xsl:param name="altova:nodeRow" as="node()"/>
        <xsl:param name="altova:nColumn" as="xs:integer"/>
        <xsl:param name="altova:nCellInCurrentRow" as="xs:integer"/>
        <xsl:param name="altova:nColSpanInCellRemaining" as="xs:integer"/>
        <xsl:param name="altova:bColSpanBegins" as="xs:boolean"/>
        <!-- Also true if it's just a single cell -->
        <xsl:param name="altova:nCurrentSum" as="xs:integer"/>
        <xsl:param name="altova:nRowSpansToAdd" as="xs:integer"/>
        <xsl:choose>
            <xsl:when test="$altova:nColumn gt count($altova:seqActiveRowSpans)">
                <xsl:sequence select="()"/>
            </xsl:when>
            <!-- If the cell is not under a rowspan -->
            <xsl:when test="$altova:seqActiveRowSpans[$altova:nColumn] eq 0">
                <!-- If the cell is not under a colspan -->
                <xsl:if test="$altova:bColSpanBegins eq true()">
                    <!-- A non-spanned cell starts in this column -->
                    <altova:ColumnIndex>
                        <xsl:sequence select="$altova:nCurrentSum + 1"/>
                    </altova:ColumnIndex>
                </xsl:if>
                <xsl:variable name="altova:nCellInCurrentRow_New" select="if ($altova:nColSpanInCellRemaining gt 1) then $altova:nCellInCurrentRow else $altova:nCellInCurrentRow + 1" as="xs:integer"/>
                <xsl:variable name="altova:nColSpanInCell_New" select="if ($altova:nColSpanInCellRemaining gt 1) then $altova:nColSpanInCellRemaining - 1 else if (empty(altova:GetCellFromRow($altova:nodeRow, $altova:nCellInCurrentRow_New))) then 1 else altova:col-span(altova:GetCellFromRow($altova:nodeRow, $altova:nCellInCurrentRow_New))" as="xs:integer"/>
                <xsl:variable name="altova:bColSpanBegins_New" select="$altova:nCellInCurrentRow ne $altova:nCellInCurrentRow_New" as="xs:boolean"/>
                <!--xsl:sequence select="altova:BuildTableIndexInfo_SingleRow_Recursive($altova:seqActiveRowSpans, $altova:nodeRow, $altova:nColumn + 1, $altova:nCellInCurrentRow_New, $altova:nColSpanInCell_New, $altova:bColSpanBegins_New, $altova:nCurrentSum + 1, $altova:nRowSpansToAdd)"/-->
                <xsl:call-template name="altova:BuildTableIndexInfo_SingleRow_Recursive">
                    <xsl:with-param name="altova:seqActiveRowSpans" select="$altova:seqActiveRowSpans"/>
                    <xsl:with-param name="altova:nodeRow" select="$altova:nodeRow"/>
                    <xsl:with-param name="altova:nColumn" select="$altova:nColumn + 1"/>
                    <xsl:with-param name="altova:nCellInCurrentRow" select="$altova:nCellInCurrentRow_New"/>
                    <xsl:with-param name="altova:nColSpanInCellRemaining" select="$altova:nColSpanInCell_New"/>
                    <xsl:with-param name="altova:bColSpanBegins" select="$altova:bColSpanBegins_New"/>
                    <xsl:with-param name="altova:nCurrentSum" select="$altova:nCurrentSum + 1"/>
                    <xsl:with-param name="altova:nRowSpansToAdd" select="$altova:nRowSpansToAdd"/>
                </xsl:call-template>
            </xsl:when>
            <!-- The cell is under a rowspan -->
            <xsl:otherwise>
                <!--xsl:sequence select="altova:BuildTableIndexInfo_SingleRow_Recursive($altova:seqActiveRowSpans, $altova:nodeRow, $altova:nColumn + 1, $altova:nCellInCurrentRow, $altova:nColSpanInCellRemaining, $altova:bColSpanBegins, $altova:nCurrentSum + 1, $altova:nRowSpansToAdd + 1)"/-->
                <xsl:call-template name="altova:BuildTableIndexInfo_SingleRow_Recursive">
                    <xsl:with-param name="altova:seqActiveRowSpans" select="$altova:seqActiveRowSpans"/>
                    <xsl:with-param name="altova:nodeRow" select="$altova:nodeRow"/>
                    <xsl:with-param name="altova:nColumn" select="$altova:nColumn + 1"/>
                    <xsl:with-param name="altova:nCellInCurrentRow" select="$altova:nCellInCurrentRow"/>
                    <xsl:with-param name="altova:nColSpanInCellRemaining" select="$altova:nColSpanInCellRemaining"/>
                    <xsl:with-param name="altova:bColSpanBegins" select="$altova:bColSpanBegins"/>
                    <xsl:with-param name="altova:nCurrentSum" select="$altova:nCurrentSum + 1"/>
                    <xsl:with-param name="altova:nRowSpansToAdd" select="$altova:nRowSpansToAdd + 1"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    <xsl:function name="altova:BuildTableIndexInfo_ActiveRowSpans_Recursive" as="xs:integer*" xpath-default-namespace="">
        <xsl:param name="altova:seqRowSpans" as="xs:integer*"/>
        <xsl:param name="altova:nodeCurrentRow" as="node()"/>
        <xsl:param name="altova:nColumn" as="xs:integer"/>
        <xsl:param name="altova:nCellInCurrentRow" as="xs:integer"/>
        <xsl:param name="altova:nColSpanInCellRemaining" as="xs:integer"/>
        <xsl:choose>
            <xsl:when test="$altova:nColumn gt count($altova:seqRowSpans)">
                <xsl:sequence select="()"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:variable name="altova:nRowSpanForCurrentColumn" select="altova:BuildTableIndexInfo_ActiveRowSpans_SingleColumn($altova:seqRowSpans, $altova:nodeCurrentRow, $altova:nColumn, $altova:nCellInCurrentRow)" as="xs:integer?"/>
                <xsl:sequence select="$altova:nRowSpanForCurrentColumn"/>
                <xsl:variable name="altova:nCellInCurrentRow_New" select="if ($altova:seqRowSpans[$altova:nColumn] eq 0 and $altova:nColSpanInCellRemaining eq 1) then $altova:nCellInCurrentRow + 1 else $altova:nCellInCurrentRow" as="xs:integer"/>
                <xsl:variable name="altova:nColSpanInCell_New" select="if ($altova:seqRowSpans[$altova:nColumn] eq 0 and $altova:nCellInCurrentRow eq $altova:nCellInCurrentRow_New) then $altova:nColSpanInCellRemaining - 1 else if (empty(altova:GetCellFromRow($altova:nodeCurrentRow, $altova:nCellInCurrentRow_New))) then 1 else altova:col-span(altova:GetCellFromRow($altova:nodeCurrentRow, $altova:nCellInCurrentRow_New))" as="xs:integer"/>
                <xsl:sequence select="altova:BuildTableIndexInfo_ActiveRowSpans_Recursive($altova:seqRowSpans, $altova:nodeCurrentRow, $altova:nColumn + 1, $altova:nCellInCurrentRow_New, $altova:nColSpanInCell_New)"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:function>
    <xsl:function name="altova:BuildTableIndexInfo_ActiveRowSpans_SingleColumn" as="xs:integer?" xpath-default-namespace="">
        <xsl:param name="altova:seqRowSpans" as="xs:integer*"/>
        <xsl:param name="altova:nodeCurrentRow" as="node()"/>
        <xsl:param name="altova:nColumn" as="xs:integer"/>
        <xsl:param name="altova:nCellInCurrentRow" as="xs:integer"/>
        <xsl:choose>
            <xsl:when test="$altova:seqRowSpans[$altova:nColumn] gt 0">
                <xsl:sequence select="$altova:seqRowSpans[$altova:nColumn] - 1"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:variable name="altova:nodeCell" select="altova:GetCellFromRow($altova:nodeCurrentRow, $altova:nCellInCurrentRow)" as="node()"/>
                <xsl:sequence select="altova:GetRowspanFromCell($altova:nodeCell) - 1"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:function>
    <xsl:function name="altova:GetChartYValuesForSingleSeries">
        <xsl:param name="seqCategoryLeafPos" as="node()*"/>
        <xsl:param name="nodeSeriesLeafPos" as="node()"/>
        <xsl:param name="bValuesInCategory" as="xs:boolean"/>
        <xsl:for-each select="$seqCategoryLeafPos">
            <xsl:element name="altova:Value">
                <xsl:value-of select="altova:GetChartYValueForSingleSeriesPos($nodeSeriesLeafPos, ., $bValuesInCategory)"/>
            </xsl:element>
        </xsl:for-each>
    </xsl:function>
    <xsl:function name="altova:GetChartYValueForSingleSeriesPos">
        <xsl:param name="nodeSeriesLeafPos" as="node()"/>
        <xsl:param name="nodeCategoryLeafPos" as="node()"/>
        <xsl:param name="bValuesInCategory" as="xs:boolean"/>
        <xsl:variable name="altova:seqCategoryContextIds" select="$nodeCategoryLeafPos/altova:Context/@altova:ContextId" as="xs:string*"/>
        <xsl:variable name="altova:seqSeriesContextIds" select="$nodeSeriesLeafPos/altova:Context/@altova:ContextId" as="xs:string*"/>
        <xsl:variable name="altova:sCommonContextId" select="for $i in $altova:seqCategoryContextIds return if (some $j in $altova:seqSeriesContextIds satisfies $i eq $j) then $i else ()" as="xs:string*"/>
        <xsl:choose>
            <xsl:when test="count($altova:sCommonContextId) gt 1">
                <xsl:message select="concat('Found several values instead of a single one (contexts: ', string-join($altova:sCommonContextId, ', '), ').')" terminate="yes"/>
            </xsl:when>
            <xsl:when test="count($altova:sCommonContextId) lt 1">
                <xsl:message select="concat('XBRL Chart: Info: No value found for position labeled &quot;', $nodeCategoryLeafPos/@altova:sLabel, '&quot;')" terminate="no"/>
                <xsl:sequence select="'altova:no-value'"/>
            </xsl:when>
            <xsl:when test="$bValuesInCategory">
                <xsl:sequence select="xs:string($nodeCategoryLeafPos/altova:Context[@altova:ContextId eq $altova:sCommonContextId]/@altova:Value)"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:sequence select="xs:string($nodeSeriesLeafPos/altova:Context[@altova:ContextId eq $altova:sCommonContextId]/@altova:Value)"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:function>
    <xsl:function name="altova:GetChartLabelForPos" as="xs:string">
        <xsl:param name="nodeParam" as="node()"/>
        <xsl:value-of select="string-join($nodeParam/ancestor-or-self::altova:Pos/@altova:sLabel, ' ')"/>
    </xsl:function>
    <xsl:function name="altova:convert-length-to-pixel" as="xs:decimal">
        <xsl:param name="altova:length"/>
        <xsl:variable name="normLength" select="normalize-space($altova:length)"/>
        <xsl:choose>
            <xsl:when test="ends-with($normLength, 'px')">
                <xsl:value-of select="substring-before($normLength, 'px')"/>
            </xsl:when>
            <xsl:when test="ends-with($normLength, 'in')">
                <xsl:value-of select="xs:decimal(substring-before($normLength, 'in')) * $altova:nPxPerIn"/>
            </xsl:when>
            <xsl:when test="ends-with($normLength, 'cm')">
                <xsl:value-of select="xs:decimal(substring-before($normLength, 'cm')) * $altova:nPxPerIn div 2.54"/>
            </xsl:when>
            <xsl:when test="ends-with($normLength, 'mm')">
                <xsl:value-of select="xs:decimal(substring-before($normLength, 'mm')) * $altova:nPxPerIn div 25.4"/>
            </xsl:when>
            <xsl:when test="ends-with($normLength, 'pt')">
                <xsl:value-of select="xs:decimal(substring-before($normLength, 'pt')) * $altova:nPxPerIn div 72.0"/>
            </xsl:when>
            <xsl:when test="ends-with($normLength, 'pc')">
                <xsl:value-of select="xs:decimal(substring-before($normLength, 'pc')) * $altova:nPxPerIn div 6.0"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$normLength"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:function>
    <xsl:function name="altova:convert-length-to-mm" as="xs:decimal">
        <xsl:param name="altova:length"/>
        <xsl:variable name="normLength" select="normalize-space($altova:length)"/>
        <xsl:choose>
            <xsl:when test="ends-with($normLength, 'px')">
                <xsl:value-of select="xs:decimal(substring-before($normLength, 'px')) div $altova:nPxPerIn * 25.4"/>
            </xsl:when>
            <xsl:when test="ends-with($normLength, 'in')">
                <xsl:value-of select="xs:decimal(substring-before($normLength, 'in')) * 25.4"/>
            </xsl:when>
            <xsl:when test="ends-with($normLength, 'cm')">
                <xsl:value-of select="xs:decimal(substring-before($normLength, 'cm')) * 10"/>
            </xsl:when>
            <xsl:when test="ends-with($normLength, 'mm')">
                <xsl:value-of select="substring-before($normLength, 'mm') "/>
            </xsl:when>
            <xsl:when test="ends-with($normLength, 'pt')">
                <xsl:value-of select="xs:decimal(substring-before($normLength, 'pt')) * 25.4 div 72.0"/>
            </xsl:when>
            <xsl:when test="ends-with($normLength, 'pc')">
                <xsl:value-of select="xs:decimal(substring-before($normLength, 'pc')) * 25.4 div 6.0"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="number($normLength) div $altova:nPxPerIn * 25.4"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:function>
    <xsl:variable name="altova:nDefaultFontSizePt" as="xs:integer" select="12"/>
    <xsl:variable name="altova:nCmPerIn" as="xs:double" select="2.54"/>
    <xsl:variable name="altova:nPercentPerEm" as="xs:integer" select="100"/>
    <xsl:variable name="altova:nPercentPerEx" as="xs:integer" select="50"/>
    <xsl:variable name="altova:nPtPerPc" as="xs:integer" select="12"/>
    <xsl:variable name="altova:nPtPerIn" as="xs:integer" select="72"/>
    <xsl:template match="altova:Footnote" mode="second-step">
        <fo:footnote>
            <xsl:variable name="altova:nodeDocumentRoot" as="node()" select="if (ancestor::altova:result-document) then ancestor::altova:result-document[1] else /altova:main-document"/>
            <fo:inline>
                <xsl:copy-of select="@*"/>
                <fo:inline vertical-align="super" font-size="8">
                    <xsl:number format="1" level="any" count="altova:Footnote[some $anc in ancestor::* satisfies $anc is $altova:nodeDocumentRoot]"/>
                </fo:inline>
            </fo:inline>
            <fo:footnote-body>
                <fo:block>
                    <xsl:copy-of select="@*"/>
                    <fo:inline vertical-align="super" font-size="8">
                        <xsl:number format="1" level="any" count="altova:Footnote[some $anc in ancestor::* satisfies $anc is $altova:nodeDocumentRoot]"/>
                    </fo:inline>
                    <xsl:apply-templates mode="#current"/>
                </fo:block>
            </fo:footnote-body>
        </fo:footnote>
    </xsl:template>