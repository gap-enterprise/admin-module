<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:sec="http://www.surati.io/Security/User/Profile" version="2.0">
  <xsl:output method="html" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
  <xsl:include href="/xsl/layout.xsl"/>
  <xsl:template match="page" mode="head">
    <title>
      <xsl:text>GAP - </xsl:text> <xsl:value-of select="root_page/title"/>
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
            <xsl:value-of select="root_page/title"/>
            <div class="page-title-subheading opacity-10">
              <nav aria-label="breadcrumb">
                <ol class="breadcrumb">
                  <li class="breadcrumb-item">
                    <a href="/home">
                      <i aria-hidden="true" class="fa fa-home"/>
                    </a>
                  </li>
                  <li class="active breadcrumb-item" aria-current="page">
                     <xsl:value-of select="root_page/subtitle"/>
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
	             <div class="dropdown-menu-header">
	                 <div class="dropdown-menu-header-inner bg-white">
	                     <div class="menu-header-content">
	                         <div class="avatar-icon-wrapper mb-3 avatar-icon-xl">
	                             <div class="avatar-icon">
	                                 <img src="{identity/photo}" alt="Avatar 5"/>
	                             </div>
	                         </div>
	                         <div class="menu-header-btn-pane pt-1">
                                 <a href="/user/avatar/edit?id={user/id}&amp;{root_page/full}" class="btn-shadow btn-wide btn-pill mr-1 btn-hover-shine btn btn-primary btn-icon btn btn-warning btn-sm"><xsl:text>Changer photo</xsl:text></a>
                                 <a class="btn-shadow btn-wide btn-pill mr-1 btn-hover-shine btn-icon btn btn-sm btn-dark" data-style="slide-right" href="{links/link[@rel='takes:logout']/@href}">
                                     <span class="ladda-label"><xsl:text>Se déconnecter</xsl:text></span>
                                    <span class="ladda-spinner"></span><div class="ladda-progress" style="width: 0px;"></div>
                                 </a>
                             </div>
	                     </div>
	                 </div>
	             </div>
	          </div> 
              <div class="col-md-6">
                <h5><xsl:text>Nom</xsl:text></h5>
                <p><xsl:value-of select="user/name"/></p>
              </div>
              <div class="col-md-6">
              	<h5><xsl:text>Login</xsl:text></h5>
                <p><xsl:value-of select="user/login"/></p>
              </div>
            </div>
            <div class="divider"/>
            <div class="clearfix">
              <a data-toggle="collapse" href="#userInfo" class="pull-right btn-shadow btn-wide btn-pill mr-1 btn-hover-shine btn btn-primary btn-icon btn" data-style="slide-left">
                <span class="ladda-label"><i class="fa fa-id-card btn-icon-wrapper"> </i><xsl:text> Information</xsl:text></span>
                <span class="ladda-spinner"></span><div class="ladda-progress" style="width: 0px;"></div>
             </a>
		     <a href="/user-profile/change-password" class="pull-right btn-shadow btn-wide btn-pill mr-1 btn-hover-shine btn btn-primary btn-icon btn-info">
                <xsl:text>Changer mot de passe </xsl:text>
                <i class="fa fa-lock"/>
             </a>
            </div>
            <div class="divider"/>
            <ul class="list-group list-group-flush">
		        <li class="list-group-item collapse" id="userInfo">
		        	<h5 class="card-title">Modifier votre nom et prénoms</h5>
                     <form class="" method="post" action="/user-profile/update-user-name">
                     	<input name="newName" placeholder="Entrez un nouveau nom" required="" autocomplete="off" type="text" class="form-control"/>
                     	<button type="submit" class="mt-2 btn btn-info"><i class="fa fa-inbox btn-icon-wrapper"></i><xsl:text> Enregistrer</xsl:text></button>
                     </form>
		        </li>
	      </ul>
        </div>
      </div>
    </div>
  </xsl:template>
  <xsl:template match="page" mode="custom-script"></xsl:template>
</xsl:stylesheet>
