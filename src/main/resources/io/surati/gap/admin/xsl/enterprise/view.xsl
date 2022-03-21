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
      <xsl:text>GAP - Entreprise</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="header">
    <div class="app-page-title app-page-title-simple">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="lnr-apartment icon-gradient bg-sunny-morning"/>
          </div>
          <div>
            <xsl:text>Entreprise</xsl:text>
            <div class="page-title-subheading opacity-10">
              <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                  <li class="breadcrumb-item">
                    <a href="/home">
                      <i aria-hidden="true" class="fa fa-home"/>
                    </a>
                  </li>
                  <li class="active breadcrumb-item">
                    Entreprise
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
    <div class="row">
      <div class="col-md-12 col-lg-12 col-xl-12">
        <div class="card-shadow-primary profile-responsive card-border mb-3 card">
          <div class="dropdown-menu-header">
            <div class="dropdown-menu-header-inner bg-focus">
              <div class="menu-header-image opacity-3"/>
              <div class="menu-header-content btn-pane-right">
                <div class="avatar-icon-wrapper mr-2 avatar-icon-xl">
                  <div class="avatar-icon rounded">
                    <img src="{logo}" alt="Enterprise logo"/>
                  </div>
                </div>
                <div class="menu-header-btn-pane">
                  <xsl:if test="sec:hasAccess(.,'CONFIGURER_INFO_ENTREPRISE')">
                    <a href="/enterprise/logo/edit" class="btn-icon mr-2 btn-icon-only btn btn-warning btn-sm">
                      <span class="mr-2 opacity-7">
                        <i class="fa fa-image"/>
                      </span>
                      <span class="mr-1">
                        <xsl:text>Changer logo</xsl:text>
                      </span>
                    </a>
                    <a href="/enterprise/edit" class="btn-icon mr-2 btn-icon-only btn btn-info btn-sm">
                      <span class="mr-2 opacity-7">
                        <i class="fa fa-edit"/>
                      </span>
                      <span class="mr-1">
                        <xsl:text>Editer</xsl:text>
                      </span>
                    </a>
                  </xsl:if>
                </div>
              </div>
            </div>
          </div>
          <div class="no-gutters row">
            <div class="col-md-12 col-lg-12">
              <div class="card-header-tab card-header">
                <div class="card-header-title font-size-lg text-capitalize font-weight-bold">
                  <i class="header-icon lnr-book icon-gradient bg-malibu-beach"> </i>
                  <xsl:text>Informations générales</xsl:text>
                </div>
              </div>
            </div>
            <div class="col-md-12 col-lg-4">
              <ul class="list-group list-group-flush">
                <li class="bg-transparent list-group-item">
                  <div class="widget-content p-0">
                    <div class="widget-content-outer">
                      <div class="widget-content-wrapper">
                        <div class="widget-content-left">
                          <div class="widget-heading">
                            <xsl:text>Intitulé</xsl:text>
                          </div>
                          <div class="widget-subheading">
                            <xsl:value-of select="name"/>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="col-md-12 col-lg-4">
              <ul class="list-group list-group-flush">
                <li class="bg-transparent list-group-item">
                  <div class="widget-content p-0">
                    <div class="widget-content-outer">
                      <div class="widget-content-wrapper">
                        <div class="widget-content-left">
                          <div class="widget-heading">
                            <xsl:text>Abrégé</xsl:text>
                          </div>
                          <div class="widget-subheading">
                            <xsl:value-of select="abbreviated"/>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="col-md-12 col-lg-4">
              <ul class="list-group list-group-flush">
                <li class="bg-transparent list-group-item">
                  <div class="widget-content p-0">
                    <div class="widget-content-outer">
                      <div class="widget-content-wrapper">
                        <div class="widget-content-left">
                          <div class="widget-heading">
                            <xsl:text>NCC</xsl:text>
                          </div>
                          <div class="widget-subheading">
                            <xsl:value-of select="ncc"/>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="col-md-12 col-lg-12">
              <div class="card-header-tab card-header">
                <div class="card-header-title font-size-lg text-capitalize font-weight-bold">
                  <i class="header-icon lnr-map icon-gradient bg-ripe-malin"> </i>
                  <xsl:text>Localisation</xsl:text>
                </div>
              </div>
            </div>
            <div class="col-md-12 col-lg-3">
              <ul class="list-group list-group-flush">
                <li class="bg-transparent list-group-item">
                  <div class="widget-content p-0">
                    <div class="widget-content-outer">
                      <div class="widget-content-wrapper">
                        <div class="widget-content-left">
                          <div class="widget-heading">
                            <xsl:text>Siège social</xsl:text>
                          </div>
                          <div class="widget-subheading">
                            <xsl:value-of select="headquarters"/>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="col-md-12 col-lg-3">
              <ul class="list-group list-group-flush">
                <li class="bg-transparent list-group-item">
                  <div class="widget-content p-0">
                    <div class="widget-content-outer">
                      <div class="widget-content-wrapper">
                        <div class="widget-content-left">
                          <div class="widget-heading">
                            <xsl:text>Pays</xsl:text>
                          </div>
                          <div class="widget-subheading">
                            <xsl:value-of select="country"/>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="col-md-12 col-lg-3">
              <ul class="list-group list-group-flush">
                <li class="bg-transparent list-group-item">
                  <div class="widget-content p-0">
                    <div class="widget-content-outer">
                      <div class="widget-content-wrapper">
                        <div class="widget-content-left">
                          <div class="widget-heading">
                            <xsl:text>Ville</xsl:text>
                          </div>
                          <div class="widget-subheading">
                            <xsl:value-of select="city"/>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="col-md-12 col-lg-3">
              <ul class="list-group list-group-flush">
                <li class="bg-transparent list-group-item">
                  <div class="widget-content p-0">
                    <div class="widget-content-outer">
                      <div class="widget-content-wrapper">
                        <div class="widget-content-left">
                          <div class="widget-heading">
                            <xsl:text>Adresse postale</xsl:text>
                          </div>
                          <div class="widget-subheading">
                            <xsl:value-of select="address"/>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="col-md-12 col-lg-12">
              <div class="card-header-tab card-header">
                <div class="card-header-title font-size-lg text-capitalize font-weight-bold">
                  <i class="header-icon lnr-briefcase icon-gradient bg-sunny-morning"> </i>
                  <xsl:text>Adresse et contacts</xsl:text>
                </div>
              </div>
            </div>
            <div class="col-md-12 col-lg-3">
              <ul class="list-group list-group-flush">
                <li class="bg-transparent list-group-item">
                  <div class="widget-content p-0">
                    <div class="widget-content-outer">
                      <div class="widget-content-wrapper">
                        <div class="widget-content-left">
                          <div class="widget-heading">
                            <xsl:text>Tel 1</xsl:text>
                          </div>
                          <div class="widget-subheading">
                            <xsl:value-of select="tel1"/>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="col-md-12 col-lg-3">
              <ul class="list-group list-group-flush">
                <li class="bg-transparent list-group-item">
                  <div class="widget-content p-0">
                    <div class="widget-content-outer">
                      <div class="widget-content-wrapper">
                        <div class="widget-content-left">
                          <div class="widget-heading">
                            <xsl:text>Tel 2</xsl:text>
                          </div>
                          <div class="widget-subheading">
                            <xsl:value-of select="tel2"/>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="col-md-12 col-lg-3">
              <ul class="list-group list-group-flush">
                <li class="bg-transparent list-group-item">
                  <div class="widget-content p-0">
                    <div class="widget-content-outer">
                      <div class="widget-content-wrapper">
                        <div class="widget-content-left">
                          <div class="widget-heading">
                            <xsl:text>Email</xsl:text>
                          </div>
                          <div class="widget-subheading">
                            <xsl:value-of select="email"/>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="col-md-12 col-lg-3">
              <ul class="list-group list-group-flush">
                <li class="bg-transparent list-group-item">
                  <div class="widget-content p-0">
                    <div class="widget-content-outer">
                      <div class="widget-content-wrapper">
                        <div class="widget-content-left">
                          <div class="widget-heading">
                            <xsl:text>Site web</xsl:text>
                          </div>
                          <div class="widget-subheading">
                            <a href="http://{website}" target="_blank">
                              <xsl:value-of select="website"/>
                            </a>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="col-md-12 col-lg-12">
              <div class="card-header-tab card-header">
                <div class="card-header-title font-size-lg text-capitalize font-weight-bold">
                  <i class="header-icon lnr-user icon-gradient bg-love-kiss"> </i>
                  <xsl:text>Représentant</xsl:text>
                </div>
              </div>
            </div>
            <div class="col-md-12 col-lg-4">
              <ul class="list-group list-group-flush">
                <li class="bg-transparent list-group-item">
                  <div class="widget-content p-0">
                    <div class="widget-content-outer">
                      <div class="widget-content-wrapper">
                        <div class="widget-content-left">
                          <div class="widget-heading">
                            <xsl:text>Nom complet</xsl:text>
                          </div>
                          <div class="widget-subheading">
                            <xsl:value-of select="representative"/>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="col-md-12 col-lg-4">
              <ul class="list-group list-group-flush">
                <li class="bg-transparent list-group-item">
                  <div class="widget-content p-0">
                    <div class="widget-content-outer">
                      <div class="widget-content-wrapper">
                        <div class="widget-content-left">
                          <div class="widget-heading">
                            <xsl:text>Poste</xsl:text>
                          </div>
                          <div class="widget-subheading">
                            <xsl:value-of select="representative_position"/>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="col-md-12 col-lg-4">
              <ul class="list-group list-group-flush">
                <li class="bg-transparent list-group-item">
                  <div class="widget-content p-0">
                    <div class="widget-content-outer">
                      <div class="widget-content-wrapper">
                        <div class="widget-content-left">
                          <div class="widget-heading">
                            <xsl:text>Civilité</xsl:text>
                          </div>
                          <div class="widget-subheading">
                            <xsl:value-of select="representative_civility"/>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="col-md-12 col-lg-12">
              <div class="card-header-tab card-header">
                <div class="card-header-title font-size-lg text-capitalize font-weight-bold">
                  <i class="header-icon lnr-database icon-gradient bg-grow-early"> </i>
                  <xsl:text>Exportation des données</xsl:text>
                </div>
              </div>
            </div>
            <div class="col-md-12 col-lg-4">
              <ul class="list-group list-group-flush">
                <li class="bg-transparent list-group-item">
                  <div class="widget-content p-0">
                    <div class="widget-content-outer">
                      <div class="widget-content-wrapper">
                        <div class="widget-content-left">
                          <div class="widget-heading">
                            <xsl:text>Emplacement d'exportation</xsl:text>
                          </div>
                          <div class="widget-subheading">
                            <xsl:value-of select="export_location"/>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <div class="col-md-12 col-lg-12">
              <div class="card-header-tab card-header">
                <div class="card-header-title font-size-lg text-capitalize font-weight-bold">
                  <i class="header-icon lnr-layers icon-gradient bg-plum-plate"> </i>
                  <xsl:text>Type document de référence</xsl:text>
                </div>
              </div>
            </div>
            <div class="col-md-12 col-lg-4">
              <ul class="list-group list-group-flush">
                <li class="bg-transparent list-group-item">
                  <div class="widget-content p-0">
                    <div class="widget-content-outer">
                      <div class="widget-content-wrapper">
                        <div class="widget-content-left">
                          <div class="widget-heading">
                            <xsl:text>Type document de référence par défaut</xsl:text>
                          </div>
                          <div class="widget-subheading">
                            <xsl:value-of select="default_reference_document_type/name"/>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="custom-script"/>
</xsl:stylesheet>
