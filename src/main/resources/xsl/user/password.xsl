<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
  <xsl:output method="html" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  <xsl:include href="/xsl/layout.xsl"/>
  <xsl:template match="page" mode="head">
    <title>
      <xsl:text>GAP - Utilisateurs</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="header">
    <xsl:variable name="is_new" select="not(item/id)"/>
    <div class="app-page-title app-page-title-simple">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="lnr-user icon-gradient bg-night-fade"/>
          </div>
          <div>
            <xsl:text>Utilisateur</xsl:text>
            <div class="page-title-subheading">
              <xsl:text>Modifier le mot de passe</xsl:text>
            </div>
          </div>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="main-card mb-3 card">
      <div class="card">
        <div class="card-body">
          <form id="signupForm" action="/user/update-password" method="post">
            <div class="form-row">
              <input name="id" type="hidden" value="{item/id}"/>
              <div class="col-md-6">
                <div class="position-relative form-group">
                  <label for="password" class="">
                    <xsl:text>Mot de passe</xsl:text>
                    <span style="color: red"> *</span>
                  </label>
                  <input name="password" id="password" placeholder="Entrez un mot de passe ..." type="password" class="form-control" required=""/>
                </div>
              </div>
              <div class="col-md-6">
                <div class="position-relative form-group">
                  <label for="confirm_password" class="">
                    <xsl:text>Confirmer le mot de passe</xsl:text>
                    <span style="color: red"> *</span>
                  </label>
                  <input id="confirm_password" name="confirm_password" placeholder="Veuillez confirmer le nouveau mot de passe ..." type="password" class="form-control" autocomplete="off" required=""/>
                </div>
              </div>
            </div>
            <div class="divider"/>
            <div class="clearfix">
              <button type="submit" class="btn-shadow btn-wide float-right btn-pill btn-hover-shine btn btn-primary">
                <xsl:text>Enregistrer </xsl:text>
                <i class="fa fa-check"/>
              </button>
              <button type="button" onclick="location.href='/user'" class="btn-shadow float-right btn-wide btn-pill mr-1 btn btn-outline-secondary">
               	<xsl:attribute name="onclick">
                  <xsl:text>location.href='/user/view?id=</xsl:text>
                  <xsl:value-of select="item/id"/>
                  <xsl:text>'</xsl:text>
                </xsl:attribute>
                <xsl:text>Annuler </xsl:text>
                <i class="fa fa-undo"/>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="custom-script"></xsl:template>
</xsl:stylesheet>
