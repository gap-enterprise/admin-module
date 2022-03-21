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
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:sec="http://www.surati.io/Security/User/Profile" version="2.0">
  <xsl:output method="html" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  <xsl:include href="/io/surati/gap/admin/xsl/layout.xsl"/>
  <xsl:template match="page" mode="head">
    <title>
      <xsl:text>GAP - Utilisateurs</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="header">
    <div class="app-page-title app-page-title-simple">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="lnr-user icon-gradient bg-night-fade"/>
          </div>
          <div>
            <xsl:text>Utilisateurs</xsl:text>
            <div class="page-title-subheading">
              <xsl:text>Visualiser l'utilisateur </xsl:text>
              <xsl:value-of select="item/name"/>
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
          <div class="row">
            <div class="col-md-12">
              <div class="dropdown-menu-header">
                <div class="dropdown-menu-header-inner bg-white">
                  <div class="menu-header-content">
                    <div class="avatar-icon-wrapper mb-3 avatar-icon-xl">
                      <div class="avatar-icon">
                        <img src="{item/photo}" alt="Avatar 5"/>
                      </div>
                    </div>
                    <div class="menu-header-btn-pane pt-1">
                      <a href="/user/admin/avatar/edit?id={item/id}" class="btn-shadow btn-wide btn-pill mr-1 btn-hover-shine btn btn-primary btn-icon btn btn-warning btn-sm">
                        <xsl:text>Changer de photo</xsl:text>
                      </a>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-md-4">
              <h5>
                <xsl:text>Nom</xsl:text>
              </h5>
              <p>
                <xsl:value-of select="item/name"/>
              </p>
            </div>
            <div class="col-md-4">
              <h5>
                <xsl:text>Login</xsl:text>
              </h5>
              <p>
                <xsl:value-of select="item/login"/>
              </p>
            </div>
            <div class="col-md-4">
              <h5>
                <xsl:text>Profil</xsl:text>
              </h5>
              <p>
                <xsl:value-of select="item/profile"/>
              </p>
            </div>
            <xsl:if test="item/blocked = 'true'">
              <div class="col-md-12">
                <p>
                  <i>L'utilisateur est actuellement bloqué</i>
                </p>
              </div>
            </xsl:if>
          </div>
          <div class="divider"/>
          <div class="clearfix">
            <a href="/user" class="btn-shadow float-right btn-wide btn-pill btn btn-outline-secondary">
              <xsl:text>Retourner </xsl:text>
              <i class="fa fa-arrow-left"/>
            </a>
            <xsl:if test="sec:hasAccess(.,'CONFIGURER_UTILISATEURS')">
              <a href="/user/edit" class="btn-shadow btn-wide float-right btn-pill mr-1 btn-hover-shine btn btn-success">
                <xsl:text>Nouveau </xsl:text>
                <i class="fa fa-file"/>
              </a>
              <a href="/user/delete?id={item/id}" class="btn-shadow btn-wide float-right mr-1 btn-pill btn-hover-shine btn btn-danger" onclick="return confirm('Voulez-vous supprimer cet utilisateur ?');">
                <xsl:text>Supprimer </xsl:text>
                <i class="fa fa-trash"/>
              </a>
              <a href="/user/edit?id={item/id}" class="btn-shadow btn-wide float-right btn-pill mr-1 btn-hover-shine btn btn-primary">
                <xsl:text>Modifier </xsl:text>
                <i class="fa fa-edit"/>
              </a>
            </xsl:if>
            <xsl:if test="sec:hasAccess(.,'BLOQUER_UTILISATEURS')">
              <xsl:if test="item/blocked = 'true'">
                <a href="/user/block?enable=false&amp;id={item/id}" class="btn-shadow btn-wide float-right btn-pill mr-1 btn-hover-shine btn btn-focus">
                  <xsl:text>Débloquer </xsl:text>
                  <i class="fa fa-unlock-alt"/>
                </a>
              </xsl:if>
              <xsl:if test="item/blocked = 'false'">
                <a href="/user/block?enable=true&amp;id={item/id}" class="btn-shadow btn-wide float-right btn-pill mr-1 btn-hover-shine btn btn-warning">
                  <xsl:text>Bloquer </xsl:text>
                  <i class="fa fa-lock"/>
                </a>
              </xsl:if>
            </xsl:if>
            <xsl:if test="sec:hasAccess(.,'CHANGER_MOT_DE_PASSE_UTILISATEURS')">
              <a href="/user/password?id={item/id}" class="ladda-button btn btn-pill btn-gradient-light mr-2 btn btn-sm">
                <xsl:text>Changer mot de passe </xsl:text>
                <i class="fa fa-lock"/>
              </a>
            </xsl:if>
          </div>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="custom-script"/>
</xsl:stylesheet>
