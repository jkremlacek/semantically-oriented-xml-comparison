<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:xdiff="http://www.fi.muni.cz/courses/PB138/j2014/projects/soxc/xdiff"
                xmlns="http://www.w3.org/1999/xhtml">
    
    <xsl:output method="xml"
                doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
                doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"
                encoding="UTF-8"
                indent="no"
    />
    
    <xsl:template match="/">
        <html>
            <head>
                <title>XDiff HTML view</title>
                <style type="text/css">
                    .both { }
                    .left {
                        color: green;
                    }
                    .right {
                        color: red;
                    }
                </style>
            </head>
            <body>
                <xsl:apply-templates select="xdiff:xdiff/*"/>
            </body>
        </html>
    </xsl:template>
    
    <xsl:template match="xdiff:document">
        <span class="{@side}">
            <xsl:apply-templates select="xdiff:children/*"/>
        </span>
    </xsl:template>
    
    <xsl:template match="xdiff:prefix">
        <span class="{@side}">
            <xsl:value-of select="."/>
        </span>
    </xsl:template>

    <xsl:template match="xdiff:localName">
        <span class="{@side}">
            <xsl:value-of select="."/>
        </span>
    </xsl:template>
    
    <xsl:template match="xdiff:data">
        <span class="{@side}">
            <xsl:value-of select="."/>
        </span>
    </xsl:template>

    <xsl:template match="xdiff:processingInstruction">
        <span class="{@side}">
            <xsl:text>&lt;?</xsl:text>
            <xsl:value-of select="xdiff:target"/>
            <xsl:text> </xsl:text>
            <xsl:apply-templates select="xdiff:data"/>
            <xsl:text>?&gt;</xsl:text>
        </span>
    </xsl:template>

    <xsl:template match="xdiff:comment">
        <span class="{@side}">
            <xsl:text>&lt;!--</xsl:text>
            <xsl:apply-templates select="xdiff:data"/>
            <xsl:text>--&gt;</xsl:text>
        </span>
    </xsl:template>

    <xsl:template match="xdiff:text">
        <span class="{@side}">
            <xsl:apply-templates select="xdiff:data"/>
        </span>
    </xsl:template>

    <xsl:template match="xdiff:cdataSection">
        <span class="{@side}">
            <xsl:text>&lt;![CDATA[</xsl:text>
            <xsl:apply-templates select="xdiff:data"/>
            <xsl:text>]]&gt;</xsl:text>
        </span>
    </xsl:template>

    <xsl:template match="xdiff:entityReference">
        <span class="{@side}">
            <xsl:text>&amp;</xsl:text>
            <xsl:value-of select="xdiff:name"/>
            <xsl:text>;</xsl:text>
        </span>
    </xsl:template>

    <xsl:template match="xdiff:attribute">
        <span class="{@side}">
            <xsl:text> </xsl:text>
            <xsl:apply-templates select="." mode="name"/>
            <xsl:text>="</xsl:text>
            <xsl:apply-templates select="xdiff:children/*"/>
            <xsl:text>"</xsl:text>
        </span>
    </xsl:template>

    <xsl:template match="*" mode="name">
        <xsl:apply-templates select="xdiff:prefix"/>
        <xsl:if test="xdiff:prefix">
            <xsl:text>:</xsl:text>
        </xsl:if>
        <xsl:apply-templates select="xdiff:localName"/>
    </xsl:template>
    
    <xsl:template match="xdiff:element">
        <span class="{@side}">
            <xsl:text>&lt;</xsl:text>
            <xsl:apply-templates select="." mode="name"/>
            <xsl:apply-templates select="xdiff:attributes/*"/>
            <xsl:text>&gt;</xsl:text>
            
            <xsl:apply-templates select="xdiff:children/*"/>
            
            <xsl:text>&lt;/</xsl:text>
            <xsl:apply-templates select="." mode="name"/>
            <xsl:text>&gt;</xsl:text>
        </span>
    </xsl:template>

    <xsl:template match="*">?</xsl:template>

</xsl:stylesheet>