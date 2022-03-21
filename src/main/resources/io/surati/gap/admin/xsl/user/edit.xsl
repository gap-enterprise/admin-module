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
  <xsl:include href="/io/surati/gap/admin/xsl/layout.xsl"/>
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
              <xsl:choose>
                <xsl:when test="$is_new">
                  <xsl:text>Enregistrer un utilisateur</xsl:text>
                </xsl:when>
                <xsl:otherwise>
                  <xsl:text>Modifier un utilisateur</xsl:text>
                </xsl:otherwise>
              </xsl:choose>
            </div>
          </div>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <xsl:variable name="is_new" select="not(item/id)"/>
    <div class="main-card mb-3 card">
      <div class="card">
        <div class="card-body">
          <form action="/user/save" method="post">
            <xsl:if test="not($is_new)">
              <input name="id" type="hidden" value="{item/id}"/>
            </xsl:if>
            <div class="form-row">
              <div class="col-md-12">
                <div class="position-relative form-group">
                  <label for="login" class="">
                    <xsl:text>Login</xsl:text>
                    <span style="color: red"> *</span>
                  </label>
                  <xsl:choose>
                    <xsl:when test="$is_new">
                      <input name="login" id="login" value="{item/login}" placeholder="Entrez le login ..." type="text" class="form-control" required=""/>
                    </xsl:when>
                    <xsl:otherwise>
                      <p>
                        <xsl:value-of select="item/login"/>
                      </p>
                    </xsl:otherwise>
                  </xsl:choose>
                </div>
              </div>
              <div class="col-md-6">
                <div class="position-relative form-group">
                  <label for="name" class="">
                    <xsl:text>Nom</xsl:text>
                    <span style="color: red"> *</span>
                  </label>
                  <input name="name" id="name" value="{item/name}" placeholder="Entrez un nom ..." type="text" class="form-control" required=""/>
                </div>
              </div>
              <div class="col-md-6">
                <div class="position-relative form-group">
                  <label for="profile_id" class="">
                    <xsl:text>Profil</xsl:text>
                    <span style="color: red"> *</span>
                  </label>
                  <select name="profile_id" class="form-control" required="">
                    <xsl:for-each select="profiles/profile">
                      <option value="{id}">
                        <xsl:if test="not($is_new) and id = ../../item/profile_id">
                          <xsl:attribute name="selected">selected</xsl:attribute>
                        </xsl:if>
                        <xsl:value-of select="name"/>
                      </option>
                    </xsl:for-each>
                  </select>
                </div>
              </div>
            </div>
            <div class="divider"/>
            <div class="clearfix">
              <div class="badge badge-pill badge-warning">Le mot de passe par défaut est : </div>
              <div class="badge badge-pill badge-success text-lowercase">gap</div>
              <button type="submit" class="btn-shadow btn-wide float-right btn-pill btn-hover-shine btn btn-primary">
                <xsl:choose>
                  <xsl:when test="$is_new">
                    <xsl:text>Enregistrer </xsl:text>
                  </xsl:when>
                  <xsl:otherwise>
                    <xsl:text>Modifier </xsl:text>
                  </xsl:otherwise>
                </xsl:choose>
                <i class="fa fa-check"/>
              </button>
              <button type="button" onclick="location.href='/user'" class="btn-shadow float-right btn-wide btn-pill mr-1 btn btn-outline-secondary">
                <xsl:if test="not($is_new)">
                  <xsl:attribute name="onclick">
                    <xsl:text>location.href='/user/view?id=</xsl:text>
                    <xsl:value-of select="item/id"/>
                    <xsl:text>'</xsl:text>
                  </xsl:attribute>
                </xsl:if>
                <xsl:text>Annuler </xsl:text>
                <i class="fa fa-undo"/>
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="custom-script"/>
</xsl:stylesheet>
