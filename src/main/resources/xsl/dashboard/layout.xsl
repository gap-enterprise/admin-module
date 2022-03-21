<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:sec="http://www.surati.io/Security/User/Profile" version="2.0">
  <xsl:output method="html" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  <xsl:include href="/xsl/layout.xsl"/>
  <xsl:template match="page" mode="head">
    <title>
      <xsl:text>GAP - Tableau de bord</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="header">
    <div class="app-page-title app-page-title-simple">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div>
            <div class="page-title-head center-elem">
              <span class="d-inline-block pr-2">
                <i class="lnr-apartment opacity-6"/>
              </span>
              <span class="d-inline-block">Tableau de bord</span>
            </div>
            <div class="page-title-subheading opacity-10">
              <nav class="" aria-label="breadcrumb">
                <ol class="breadcrumb">
                  <li class="breadcrumb-item">
                    <a>
                      <i aria-hidden="true" class="fa fa-home"/>
                    </a>
                  </li>
                  <li class="active breadcrumb-item" aria-current="page">
                      Tableau de bord
                  </li>
                </ol>
              </nav>
            </div>
          </div>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <xsl:if test="sec:hasAccess(.,'VISUALISER_TABLEAU_BORD_PAIEMENTS') or sec:hasAccess(.,'VISUALISER_TABLEAU_BORD_CARNETS') or sec:hasAccess(.,'VISUALISER_TABLEAU_BORD_COMPTES_BANCAIRES')">
    <div class="container">
        <ul class="tabs-animated-shadow tabs-animated nav nav-justified tabs-rounded-lg">
            <xsl:if test="sec:hasAccess(.,'VISUALISER_TABLEAU_BORD_PAIEMENTS')">
            <li class="nav-item">
                <a role="tab" class="nav-link show" href="/dashboard/payment" aria-selected="false">
	                <xsl:if test="submenu = 'payment'">
		                <xsl:attribute name="class">
		                	nav-link show active
		                </xsl:attribute>
		                <xsl:attribute name="aria-selected">
		                	true
		                </xsl:attribute>
	                </xsl:if>
                    <span>Paiements</span>
                </a>
            </li>
            </xsl:if>
            <xsl:if test="sec:hasAccess(.,'VISUALISER_TABLEAU_BORD_CARNETS')">
            <li class="nav-item">
                <a role="tab" class="nav-link" href="/dashboard/bank-note-book" aria-selected="false">
                    <xsl:if test="submenu = 'bank-note-book'">
		                <xsl:attribute name="class">
		                	nav-link show active
		                </xsl:attribute>
		                <xsl:attribute name="aria-selected">
		                	true
		                </xsl:attribute>
	                </xsl:if>
                    <span>Carnets de formules</span>
                </a>
            </li>
            </xsl:if>
            <xsl:if test="sec:hasAccess(.,'VISUALISER_TABLEAU_BORD_COMPTES_BANCAIRES')">
            <li class="nav-item">
                <a role="tab" class="nav-link" href="/dashboard/bank-account" aria-selected="false">
                    <xsl:if test="submenu = 'bank-account'">
		                <xsl:attribute name="class">
		                	nav-link show active
		                </xsl:attribute>
		                <xsl:attribute name="aria-selected">
		                	true
		                </xsl:attribute>
	                </xsl:if>
                    <span>Comptes bancaires</span>
                </a>
            </li>
            </xsl:if>
        </ul>
    </div>
    </xsl:if>
    <xsl:apply-templates select="." mode="dashboard"/>
  </xsl:template>
  <xsl:template match="page" mode="custom-script"></xsl:template>
</xsl:stylesheet>
