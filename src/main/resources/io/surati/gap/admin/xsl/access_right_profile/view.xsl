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
                  <li class="breadcrumb-item">
                    <a href="/profile">Profils</a>
                  </li>
                  <li class="active breadcrumb-item" aria-current="page">
                    <xsl:text/>
                    <xsl:value-of select="item/name"/>
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
    <div class="main-card mb-3 card">
      <div class="card">
        <div class="card-body">
          <div class="row">
            <div class="col-md-12">
              <h5>
                <xsl:text>Intitulé</xsl:text>
              </h5>
              <p>
                <xsl:value-of select="item/name"/>
              </p>
            </div>
          </div>
          <div class="row">
            <div class="col-md-12">
              <div class="card-header">
                <div class="card-header-title font-size-lg text-capitalize font-weight-normal">
                  <xsl:text>Droits d'accès</xsl:text>
                </div>
                <xsl:if test="sec:hasAccess(.,'CONFIGURER_PROFILS')">
                  <xsl:if test="not(item/name = 'Administrateur')">
                    <div class="btn-actions-pane-right">
                      <a href="/access-right/add?profile={item/id}" type="button" class="btn-icon btn-wide btn-outline-2x btn btn-outline-focus btn-sm d-flex">
                        <xsl:text>Ajouter un droit d'accès</xsl:text>
                        <span class="pl-2 align-middle opacity-7">
                          <i class="fa fa-plus"/>
                        </span>
                      </a>
                    </div>
                  </xsl:if>
                </xsl:if>
              </div>
              <xsl:if test="accesses[not(access)]">
                <h6 class="text-center pb-1 pt-1">
                  <xsl:text>Il n'y a aucun droit d'accès.</xsl:text>
                </h6>
              </xsl:if>
              <xsl:if test="accesses[access]">
                <div class="table-responsive">
                  <table class=" mb-0 table table-hover table-sm">
                    <thead>
                      <tr>
                        <th>N°</th>
                        <th>Intitulé</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      <xsl:for-each select="accesses/access">
                        <tr>
                          <td>
                            <xsl:value-of select="position()"/>
                          </td>
                          <td>
                            <xsl:value-of select="name"/>
                          </td>
                          <td>
                            <xsl:if test="../../sec:hasAccess(.,'CONFIGURER_PROFILS')">
                              <xsl:if test="not(../../item/name = 'Administrateur')">
                                <div role="group">
                                  <a href="/access-right/delete?id={id}&amp;profile={../../item/id}" class="mb-2 mr-2 btn btn-sm btn-outline-danger" onclick="return confirm('Voulez-vous supprimer ce droit d\'accès ?');">
                                    <i class="fa fa-trash"/>
                                  </a>
                                </div>
                              </xsl:if>
                            </xsl:if>
                          </td>
                        </tr>
                      </xsl:for-each>
                    </tbody>
                  </table>
                </div>
              </xsl:if>
            </div>
          </div>
          <div class="divider"/>
          <div class="clearfix">
            <a href="/profile" class="btn-shadow float-right btn-wide btn-pill btn btn-outline-secondary">
              <xsl:text>Retourner </xsl:text>
              <i class="fa fa-arrow-left"/>
            </a>
            <xsl:if test="sec:hasAccess(.,'CONFIGURER_PROFILS')">
              <xsl:if test="not(item/name = 'Administrateur')">
                <a href="/profile/edit" class="btn-shadow btn-wide float-right btn-pill mr-1 btn-hover-shine btn btn-success">
                  <xsl:text>Nouveau </xsl:text>
                  <i class="fa fa-file"/>
                </a>
                <a href="/profile/delete?id={item/id}" class="btn-shadow btn-wide float-right mr-1 btn-pill btn-hover-shine btn btn-danger" onclick="return confirm('Voulez-vous supprimer ce profil ?');">
                  <xsl:text>Supprimer </xsl:text>
                  <i class="fa fa-trash"/>
                </a>
                <a href="/profile/edit?id={item/id}" class="btn-shadow btn-wide float-right btn-pill mr-1 btn-hover-shine btn btn-primary">
                  <xsl:text>Modifier </xsl:text>
                  <i class="fa fa-edit"/>
                </a>
              </xsl:if>
            </xsl:if>
          </div>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="custom-script"/>
</xsl:stylesheet>
