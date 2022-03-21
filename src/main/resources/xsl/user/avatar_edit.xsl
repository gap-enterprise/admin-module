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
  <xsl:include href="/xsl/layout.xsl"/>
  <xsl:template match="page" mode="head">
    <title><xsl:text>GAP - </xsl:text><xsl:value-of select="root_page/title"/> - <xsl:value-of select="root_page/subtitle"/>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="header">
    <div class="app-page-title app-page-title-simple">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="lnr-users icon-gradient bg-night-fade"/>
          </div>
          <div>
            <xsl:value-of select="root_page/title"/>
            <div class="page-title-subheading opacity-10">
              <nav class="" aria-label="breadcrumb">
                <ol class="breadcrumb">
                  <li class="breadcrumb-item">
                    <a href="/home">
                      <i aria-hidden="true" class="fa fa-home"/>
                    </a>
                  </li>
                  <li class="breadcrumb-item">
                    <a href="{root_page/uri}">
                      <xsl:value-of select="root_page/subtitle"/>
                    </a>
                  </li>
                  <xsl:if test="not(identity/id = item/id)">
                    <li class="breadcrumb-item">
                      <a href="/user/view?id={item/id}">
                        <xsl:value-of select="item/name"/>
                      </a>
                    </li>
                  </xsl:if>
                  <li class="active breadcrumb-item" aria-current="page">
                    <xsl:text>Changer photo</xsl:text>
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
          <form action="/user/avatar/save?{root_page/full}" method="post" enctype="multipart/form-data">
            <input name="id" type="hidden" value="{item/id}"/>
            <div class="row">
              <div class="col-md-6">
                <div class="form-group">
                  <label class="form-label">Image à charger (Format de préférence : 360 x 360) <span style="color: red">*</span></label>
                  <input type="file" id="avatar" name="avatar" class="form-control" accept="image/*" aria-describedby="avatar" required=""/>
                </div>
              </div>
            </div>
            <div class="divider"/>
            <div class="clearfix">
              <button type="submit" class="btn-shadow btn-wide float-right btn-pill btn-hover-shine btn btn-primary">
                <xsl:text>Enregistrer </xsl:text>
                <i class="fa fa-check"/>
              </button>
              <button type="button" class="btn-shadow float-right btn-wide btn-pill mr-1 btn btn-outline-secondary" onclick="location.href='{root_page/uri}'">
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
