<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
  <xsl:output method="html" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  <xsl:include href="/xsl/dashboard/layout.xsl"/>
  <xsl:template match="page" mode="dashboard">
    <div class="main-card mb-3 card card-body">
    	<h6 class="text-center pb-1 pt-1">
          <xsl:text>Il n'y a aucun tableau de bord à afficher.</xsl:text>
        </h6>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="custom-script"></xsl:template>
</xsl:stylesheet>
