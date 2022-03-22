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
      <xsl:text>GAP - Journalisation</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="header">
    <div class="app-page-title app-page-title-simple">
      <div class="page-title-wrapper">
        <div class="page-title-heading">
          <div class="page-title-icon">
            <i class="lnr-layers icon-gradient bg-night-fade"/>
          </div>
          <div>
            <xsl:text>Journalisation</xsl:text>
            <div class="page-title-subheading opacity-10">
              <nav class="" aria-label="breadcrumb">
                <ol class="breadcrumb">
                  <li class="breadcrumb-item">
                    <a href="/home">
                      <i aria-hidden="true" class="fa fa-home"/>
                    </a>
                  </li>
                  <li class="breadcrumb-item">
                    <a href="/event-log">Journalisation</a>
                  </li>
                  <li class="active breadcrumb-item" aria-current="page">
                      Visualiser un évènement
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
            <div class="col-md-3">
              <h5>
                <xsl:text>Date</xsl:text>
              </h5>
              <p>
                <xsl:value-of select="item/date"/>
              </p>
            </div>
            <div class="col-md-3">
              <h5>
                <xsl:text>Auteur</xsl:text>
              </h5>
              <p>
                <xsl:value-of select="item/author"/>
              </p>
            </div>
            <div class="col-md-3">
              <h5>
                <xsl:text>Adresse IP</xsl:text>
              </h5>
              <p>
                <xsl:value-of select="item/ip_address"/>
              </p>
            </div>
            <div class="col-md-3">
              <h5>
                <xsl:text>Statut</xsl:text>
              </h5>
              <p>
                <xsl:if test="item/level_id = 'FINE'">
                  <div class="mb-2 mr-2 badge badge-success">
                    <xsl:value-of select="item/level_id"/>
                  </div>
                </xsl:if>
                <xsl:if test="item/level_id = 'WARNING'">
                  <div class="mb-2 mr-2 badge badge-warning">
                    <xsl:value-of select="item/level_id"/>
                  </div>
                </xsl:if>
                <xsl:if test="item/level_id = 'SEVERE'">
                  <div class="mb-2 mr-2 badge badge-danger">
                    <xsl:value-of select="item/level_id"/>
                  </div>
                </xsl:if>
                <xsl:if test="item/level_id = 'INFO'">
                  <div class="mb-2 mr-2 badge badge-info">
                    <xsl:value-of select="item/level_id"/>
                  </div>
                </xsl:if>
              </p>
            </div>
            <div class="col-md-12">
              <h5>
                <xsl:text>Message</xsl:text>
              </h5>
              <p>
                <xsl:value-of select="item/message"/>
              </p>
            </div>
            <div class="col-md-12">
              <h5>
                <xsl:text>Détail</xsl:text>
              </h5>
              <p>
                <xsl:value-of select="item/details"/>
              </p>
            </div>
          </div>
          <div class="divider"/>
          <div class="clearfix">
            <a href="/event-log" class="btn-shadow float-right btn-wide btn-pill btn btn-outline-secondary">
              <xsl:text>Retourner </xsl:text>
              <i class="fa fa-arrow-left"/>
            </a>
          </div>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="custom-script"/>
</xsl:stylesheet>
