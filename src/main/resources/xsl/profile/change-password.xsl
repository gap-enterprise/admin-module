<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
    <xsl:include href="/xsl/layout.xsl"/>
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
                        <label><xsl:text>Ancien mot de passe</xsl:text><span style="color: red"> *</span></label>
                        <input name="oldpass"  placeholder="Entrez l'ancien mot de passe ..." type="password" class="form-control" autocomplete="off" required=""/>
                    </div>
                    <div class="position-relative form-group">
                        <label><xsl:text>Nouveau mot de passe</xsl:text><span style="color: red"> *</span></label>
                        <input id="password" name="password" placeholder="Entrez le nouveau mot de passe ..." type="password" class="form-control" autocomplete="off" required=""/>
                    </div>
                    <div class="position-relative form-group">
                        <label><xsl:text>Confirmer nouveau mot de passe</xsl:text><span style="color: red"> *</span></label>
                        <input id="confirm_password" name="confirm_password" placeholder="Veuillez confirmer le nouveau mot de passe ..." type="password" class="form-control" autocomplete="off" required=""/>
                    </div>
                    <div class="divider"></div>
                    <div class="clearfix">
                        <button type="submit" class="btn-gradient-success btn-shadow btn-wide float-right btn-pill btn-hover-shine btn btn-success">
                         	<i class="fa fa-inbox"></i>
                         	<xsl:text> Enregistrer</xsl:text>
                         </button>
                         <button type="button" onclick="location.href='/user-profile'" class="btn-gradient-light btn-shadow float-right btn-wide btn-pill mr-3 btn btn-outline-secondary">
                            <i class="fa fa-undo"></i>
                            <xsl:text> Annuler</xsl:text>
                         </button>
                   </div>
                </form>
            </div>
           </div>
       </div>
    </xsl:template>
    <xsl:template match="page" mode="custom-script"></xsl:template>
</xsl:stylesheet>
