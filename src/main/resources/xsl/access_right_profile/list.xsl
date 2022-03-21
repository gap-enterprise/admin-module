<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:sec="http://www.surati.io/Security/User/Profile" version="2.0">
  <xsl:output method="html" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  <xsl:include href="/xsl/layout.xsl"/>
  <xsl:template match="page" mode="head">
    <title>
      <xsl:text>GAP - Profils</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="header">
    <div class="app-page-title app-page-title-simple">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="lnr-license icon-gradient bg-night-fade"/>
          </div>
          <div>
            <xsl:text>Profils</xsl:text>
            <div class="page-title-subheading opacity-10">
              <nav class="" aria-label="breadcrumb">
                <ol class="breadcrumb">
                  <li class="breadcrumb-item">
                    <a href="/home">
                      <i aria-hidden="true" class="fa fa-home"/>
                    </a>
                  </li>
                  <li class="active breadcrumb-item">
                    Profils
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
    <div class="main-card mb-3 card card-body">
      <div class="card-header">
        <div class="card-header-title font-size-lg text-capitalize font-weight-normal">
          <xsl:text>Liste des Profils</xsl:text>
        </div>
        <xsl:if test="sec:hasAccess(.,'CONFIGURER_PROFILS')">
        <div class="btn-actions-pane-right">
          <a href="/profile/edit" type="button" class="btn-icon btn-wide btn-outline-2x btn btn-outline-focus btn-sm d-flex">
            <xsl:text>Nouveau</xsl:text>
            <span class="pl-2 align-middle opacity-7">
              <i class="fa fa-plus"/>
            </span>
          </a>
        </div>
        </xsl:if>
      </div>
      <xsl:if test="profiles[not(profile)]">
        <h6 class="text-center pb-1 pt-1">
          <xsl:text>Il n'y a pas de profil pour le moment.</xsl:text>
        </h6>
      </xsl:if>
      <xsl:if test="profiles[profile]">
        <div class="table-responsive">
          <table class=" mb-0 table table-hover table-sm">
            <thead>
              <tr>
                <th>N&#xB0;</th>
                <th>Intitulé</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <xsl:for-each select="profiles/profile">
                <tr>
                  <td>
                    <xsl:value-of select="position()"/>
                  </td>
                  <td>
                    <xsl:value-of select="name"/>
                  </td>
                  <td>
                    <div role="group">
                        <a href="/profile/view?id={id}" class="mb-2 mr-2 btn btn-sm btn-outline-primary">
	                         <i class="fa fa-eye"/>
	                    </a>
	                    <xsl:if test="../../sec:hasAccess(.,'CONFIGURER_PROFILS')">
	                    <xsl:if test="not(name = 'Administrateur')">
	                    <a href="/profile/edit?id={id}" class="mb-2 mr-2 btn btn-sm btn-outline-success">
	                    	<i class="fa fa-edit"/>
	                    </a>
	                    <a href="/profile/delete?id={id}" class="mb-2 mr-2 btn btn-sm btn-outline-danger" onclick="return confirm('Voulez-vous supprimer ce profil?');">
	                         <i class="fa fa-trash"/>
                        </a>
                        </xsl:if>
                        </xsl:if>
                    </div>
                  </td>
                </tr>
              </xsl:for-each>
            </tbody>
          </table>
        </div>
      </xsl:if>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="custom-script"></xsl:template>
</xsl:stylesheet>
