<?xml version="1.0" encoding="UTF-8"?>
<!--
Copyright (c) 2022 Surati

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to read
the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
merge, publish, distribute, sublicense, and/or sell copies of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
  <xsl:output method="html" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  <xsl:include href="/io/surati/gap/web/base/xsl/layout.xsl"/>
  <xsl:template match="page" mode="head">
    <title>
      <xsl:text>GAP - Changer votre mot de passe</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="header">
    <div class="app-page-title app-page-title-simple">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="lnr-laptop-phone icon-gradient bg-sunny-morning"/>
          </div>
          <div>
            <xsl:text>Mot de passe</xsl:text>
            <div class="page-title-subheading">
              <xsl:text>Changer votre mot de passe</xsl:text>
            </div>
          </div>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="main-card mb-3">
      <div class="card">
        <div class="card-body">
          <form id="signupForm" action="/user-profile/update-change-password" method="post">
            <div class="position-relative form-group">
              <label>
                <xsl:text>Ancien mot de passe</xsl:text>
                <span style="color: red"> *</span>
              </label>
              <input name="oldpass" placeholder="Entrez l'ancien mot de passe ..." type="password" class="form-control" autocomplete="off" required=""/>
            </div>
            <div class="position-relative form-group">
              <label>
                <xsl:text>Nouveau mot de passe</xsl:text>
                <span style="color: red"> *</span>
              </label>
              <input id="password" name="password" placeholder="Entrez le nouveau mot de passe ..." type="password" class="form-control" autocomplete="off" required=""/>
            </div>
            <div class="position-relative form-group">
              <label>
                <xsl:text>Confirmer nouveau mot de passe</xsl:text>
                <span style="color: red"> *</span>
              </label>
              <input id="confirm_password" name="confirm_password" placeholder="Veuillez confirmer le nouveau mot de passe ..." type="password" class="form-control" autocomplete="off" required=""/>
            </div>
            <div class="divider"/>
            <div class="clearfix">
              <button type="submit" class="btn-gradient-success btn-shadow btn-wide float-right btn-pill btn-hover-shine btn btn-success">
                <i class="fa fa-inbox"/>
                <xsl:text> Enregistrer</xsl:text>
              </button>
              <button type="button" onclick="location.href='/user-profile'" class="btn-gradient-light btn-shadow float-right btn-wide btn-pill mr-3 btn btn-outline-secondary">
                <i class="fa fa-undo"/>
                <xsl:text> Annuler</xsl:text>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="custom-script"/>
</xsl:stylesheet>
