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
  <xsl:include href="/io/surati/gap/web/base/xsl/layout_index.xsl"/>
  <xsl:template match="page" mode="head">
    <title>
      <xsl:text>GAP - Gestion Automatisée des Paiements</xsl:text>
    </title>
  </xsl:template>
  <xsl:template match="page" mode="body">
    <div class="app-container app-theme-white body-tabs-shadow closed-sidebar-mobile closed-sidebar">
      <div class="app-container closed-sidebar-mobile closed-sidebar">
        <div class="h-100">
          <div class="h-100 no-gutters row">
            <div class="d-none d-lg-block col-lg-4">
              <div class="slider-light">
                <div class="slick-slider">
                  <div>
                    <div class="position-relative h-100 d-flex justify-content-center align-items-center bg-plum-plate" tabindex="-1">
                      <div class="slide-img-bg" style="background-image: url('/io/surati/gap/web/base/img/paymoney.jpg');"/>
                      <div class="slider-content">
                        <h1 style="font-size: 100px;">
                          <xsl:text>GAP</xsl:text>
                          <small style="font-size: 10px">v<xsl:value-of select="version/name"/></small>
                        </h1>
                        <p style="font-size: 20px;">
                          <xsl:text>Plateforme sécurisée de paiements bancaires automatisés</xsl:text>
                        </p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="h-100 d-flex bg-white justify-content-center align-items-center col-md-12 col-lg-8">
              <div class="mx-auto app-login-box col-sm-12 col-md-10 col-lg-9">
                <xsl:call-template name="takes_flash_without_escaping">
                  <xsl:with-param name="flash" select="flash"/>
                </xsl:call-template>
                <div class="app-logo1">
                  <img src="/io/surati/gap/web/base/img/logo.png" style="height:60px; margin-bottom: 20px;"/>
                </div>
                <h4 class="mb-0">
                  <span class="d-block">
                    <xsl:text>Bienvenue,</xsl:text>
                  </span>
                  <span>
                    <xsl:text>Veuillez-vous connecter à votre compte.</xsl:text>
                  </span>
                </h4>
                <div class="divider row"/>
                <div>
                  <form action="/authenticate" method="post" class="">
                    <div class="form-row">
                      <div class="col-md-6">
                        <div class="position-relative form-group">
                          <label for="exampleEmail" class="">
                            <xsl:text>Nom utilisateur</xsl:text>
                          </label>
                          <input name="login" id="exampleEmail" maxlength="32" required="" placeholder="Entrez votre nom utilisateur ..." autocomplete="off" type="text" class="form-control">
                            <xsl:if test="auth">
                              <xsl:attribute name="value">
                                <xsl:value-of select="auth/login"/>
                              </xsl:attribute>
                            </xsl:if>
                          </input>
                        </div>
                      </div>
                      <div class="col-md-6">
                        <div class="position-relative form-group">
                          <label for="examplePassword" class="">
                            <xsl:text>Mot de passe</xsl:text>
                          </label>
                          <input name="password" id="examplePassword" maxlength="32" required="" placeholder="Entrez votre mot de passe ..." type="password" class="form-control"/>
                        </div>
                      </div>
                    </div>
                    <div class="divider row"/>
                    <div class="d-flex align-items-center">
                      <div class="ml-auto">
                        <button type="submit" class="btn btn-primary btn-lg">
                          <xsl:text>Se connecter</xsl:text>
                        </button>
                      </div>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </xsl:template>
</xsl:stylesheet>
