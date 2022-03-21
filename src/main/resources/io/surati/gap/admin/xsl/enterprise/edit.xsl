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
      <xsl:text>GAP - Entreprise</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="header">
    <xsl:variable name="is_new" select="not(item/id)"/>
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
                    Modifier les informations
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
          <div class="card-header">
            <ul class="nav nav-justified">
              <li class="nav-item">
                <a data-toggle="tab" href="#tab-eg-0" class="nav-link active">
                  <xsl:text>Informations générales </xsl:text>
                </a>
              </li>
              <li class="nav-item">
                <a data-toggle="tab" href="#tab-eg-1" class="nav-link">
                  <xsl:text>Localisation </xsl:text>
                </a>
              </li>
              <li class="nav-item">
                <a data-toggle="tab" href="#tab-eg-2" class="nav-link">
                  <xsl:text>Adresse et contacts </xsl:text>
                </a>
              </li>
              <li class="nav-item">
                <a data-toggle="tab" href="#tab-eg-3" class="nav-link">
                  <xsl:text>Représentant </xsl:text>
                </a>
              </li>
              <li class="nav-item">
                <a data-toggle="tab" href="#tab-eg-4" class="nav-link">
                  <xsl:text>Exportation de données </xsl:text>
                </a>
              </li>
              <li class="nav-item">
                <a data-toggle="tab" href="#tab-eg-5" class="nav-link">
                  <xsl:text>Type document de référence </xsl:text>
                </a>
              </li>
            </ul>
          </div>
          <form action="/enterprise/save" method="post">
            <div class="card-body">
              <div class="tab-content">
                <div class="tab-pane show active" id="tab-eg-0" role="tabpanel">
                  <div class="form-row">
                    <div class="col-md-12">
                      <div class="position-relative form-group">
                        <label for="name" class="">
                          <xsl:text>Intitulé</xsl:text>
                          <span style="color: red"> *</span>
                        </label>
                        <input name="name" id="name" value="{name}" placeholder="Entrez l'intitulé ..." type="text" class="form-control" required=""/>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="position-relative form-group">
                        <label for="abbreviated" class="">
                          <xsl:text>Abrégé</xsl:text>
                          <span style="color: red"> *</span>
                        </label>
                        <input name="abbreviated" id="abbreviated" value="{abbreviated}" placeholder="Entrez l'abrégé ..." type="text" class="form-control" required=""/>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="position-relative form-group">
                        <label for="ncc" class="">
                          <xsl:text>Numéro de compte contribuable (NCC)</xsl:text>
                        </label>
                        <input name="ncc" id="ncc" value="{ncc}" placeholder="Entrez le numéro de compte contribuable ..." type="text" class="form-control"/>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="tab-pane" id="tab-eg-1" role="tabpanel">
                  <div class="form-row">
                    <div class="col-md-12">
                      <div class="position-relative form-group">
                        <label for="headquarters" class="">
                          <xsl:text>Siège social</xsl:text>
                        </label>
                        <input name="headquarters" id="headquarters" value="{headquarters}" placeholder="Entrez le siège social ..." type="text" class="form-control"/>
                      </div>
                    </div>
                    <div class="col-md-4">
                      <div class="position-relative form-group">
                        <label for="country" class="">
                          <xsl:text>Pays</xsl:text>
                          <span style="color: red"> *</span>
                        </label>
                        <input name="country" id="country" value="{country}" placeholder="Entrez le pays ..." type="text" class="form-control" required=""/>
                      </div>
                    </div>
                    <div class="col-md-4">
                      <div class="position-relative form-group">
                        <label for="city" class="">
                          <xsl:text>Ville</xsl:text>
                          <span style="color: red"> *</span>
                        </label>
                        <input name="city" id="city" value="{city}" placeholder="Entrez la ville ..." type="text" class="form-control" required=""/>
                      </div>
                    </div>
                    <div class="col-md-4">
                      <div class="position-relative form-group">
                        <label for="address" class="">
                          <xsl:text>Adresse postale</xsl:text>
                        </label>
                        <input name="address" id="address" value="{address}" placeholder="Entrez l'Adresse postale ..." type="text" class="form-control"/>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="tab-pane" id="tab-eg-2" role="tabpanel">
                  <div class="form-row">
                    <div class="col-md-6">
                      <div class="position-relative form-group">
                        <label for="tel1" class="">
                          <xsl:text>Téléphone 1</xsl:text>
                        </label>
                        <input name="tel1" id="tel1" value="{tel1}" placeholder="Entrez le numéro de téléphone ..." type="text" class="form-control"/>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="position-relative form-group">
                        <label for="tel2" class="">
                          <xsl:text>Téléphone 2</xsl:text>
                        </label>
                        <input name="tel2" id="tel2" value="{tel2}" placeholder="Entrez le numéro de téléphone ..." type="text" class="form-control"/>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="position-relative form-group">
                        <label for="email" class="">
                          <xsl:text>Email</xsl:text>
                          <span style="color: red"> *</span>
                        </label>
                        <input name="email" id="email" value="{email}" placeholder="Entrez le numéro de compte contribuable ..." type="email" class="form-control" required=""/>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="position-relative form-group">
                        <label for="website" class="">
                          <xsl:text>Site Web</xsl:text>
                        </label>
                        <input name="website" id="website" value="{website}" placeholder="Entrez le site internet ..." type="text" class="form-control"/>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="tab-pane" id="tab-eg-3" role="tabpanel">
                  <div class="form-row">
                    <div class="col-md-6">
                      <div class="position-relative form-group">
                        <label for="representative" class="">
                          <xsl:text>Nom complet </xsl:text>
                        </label>
                        <input name="representative" id="representative" value="{representative}" placeholder="Entrez le représentant ..." type="text" class="form-control"/>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="position-relative form-group">
                        <label for="representative_position" class="">
                          <xsl:text>Poste </xsl:text>
                        </label>
                        <input name="representative_position" id="representative_position" value="{representative_position}" placeholder="Entrez le poste occupé ..." type="text" class="form-control"/>
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="position-relative form-group">
                        <label for="representative_civility" class="">
                          <xsl:text>Civilité </xsl:text>
                        </label>
                        <input name="representative_civility" id="representative_civility" value="{representative_civility}" placeholder="Entrez la civilité ..." type="text" class="form-control"/>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="tab-pane" id="tab-eg-4" role="tabpanel">
                  <div class="form-row">
                    <div class="col-md-6">
                      <div class="position-relative form-group">
                        <label for="representative" class="">
                          <xsl:text>Emplacement d'exportation </xsl:text>
                        </label>
                        <input name="export_location" id="export_location" value="{export_location}" placeholder="Entrez un emplacement ..." type="text" class="form-control"/>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="tab-pane" id="tab-eg-5" role="tabpanel">
                  <div class="form-row">
                    <div class="col-md-6">
                      <div class="position-relative form-group">
                        <label for="representative" class="">
                          <xsl:text>Type de document de référence par défaut </xsl:text>
                          <span style="color: red"> *</span>
                        </label>
                        <select id="default_reference_document_type_id" name="default_reference_document_type_id" class="form-control" required="">
                          <option value="">-- SVP choisir un type --</option>
                          <xsl:for-each select="reference_document_types/reference_document_type">
                            <option value="{id}">
                              <xsl:if test="id = ../../default_reference_document_type_id">
                                <xsl:attribute name="selected">selected</xsl:attribute>
                              </xsl:if>
                              <xsl:value-of select="name"/>
                            </option>
                          </xsl:for-each>
                        </select>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="divider"/>
            <div class="clearfix">
              <button type="submit" class="btn-shadow btn-wide float-right btn-pill btn-hover-shine btn btn-primary">
                <xsl:text>Modifier </xsl:text>
                <i class="fa fa-check"/>
              </button>
              <button type="button" onclick="location.href='/enterprise'" class="btn-shadow float-right btn-wide btn-pill mr-1 btn btn-outline-secondary">
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
