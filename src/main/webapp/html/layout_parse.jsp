<%--
  Created by IntelliJ IDEA.
  User: guoxinggen
  Date: 9/22/16
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Android Layout Parser</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="xinggen.guo">

    <!-- Le styles -->
    <link href="/css/bootstrap.css" rel="stylesheet">
    <style type="text/css">
        body {
            padding-top: 60px;
            padding-bottom: 40px;
        }

        .sidebar-nav {
            padding: 9px 0;
        }

        @media (max-width: 980px) {
            /* Enable use of floated navbar text */
            .navbar-text.pull-right {
                float: none;
                padding-left: 5px;
                padding-right: 5px;
            }
        }

        .hidden {
            display: none;
        }

        .form-horizontal .control-group {
            margin-bottom: 0;
        }

        .form-horizontal .control-label {
            padding-right: 30px;
        }

        textarea {
            font-family: Consolas, Monaco, Lucida Console, Liberation Mono, DejaVu Sans Mono, Bitstream Vera Sans Mono, Courier New, monospace;
        }
    </style>
    <link href="/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="/js/html5shiv.js"></script>
    <![endif]-->
</head>

<body>

<script>

    function toggleClass(theEle, myclass) {

        var eClass = theEle.className;

        if (eClass.indexOf(myclass) >= 0) {
            // we already have this element hidden so remove the class.
            theEle.className = eClass.replace(myclass, '');
        } else {
            // add the class.
            theEle.className += ' ' + myclass;
        }
    }

    function changedMode() {

        var theRadio = document.getElementById("radioJava")
        var javaSettingsDiv = document.getElementById("javaSettings");
        var aaSettingsDiv = document.getElementById("aaSettings");

        toggleClass(javaSettingsDiv, "hidden");
        toggleClass(aaSettingsDiv, "hidden");

    }

</script>

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a class="brand" href="#">Android Layout Binder</a>
        </div>
    </div>
</div>

<div class="container-fluid">

    <form id="fomXml" method="post">
        <fieldset>

            <div class="row-fluid">
                <div class="span12">
                    <p>Convert your Android XML layouts into a set of declarations and binds to save you all that manual
                        typing. Enter a prefix for your fields, choose the scope paste in your XML and hit generate.
                        Select "verbose" to find out why any fields are skipped.
                    </p>
                </div>
                <!-- span 12 -->
            </div>
            <!-- row-fluid -->


            <div class="row-fluid">
                <div class="span12">

                    <div class="control-group">
                        <label class="control-label">Mode</label>

                        <div class="controls controls-row">
                            <label class="radio span2">
                                <input type="radio" name="javaOrAA" id="radioJava"
                                       value="java" checked onchange="changedMode()">
                                Standard&nbsp;Java
                            </label>
                            <label class="radio span3">
                                <input type="radio" name="javaOrAA" id="radioAA"
                                       value="aa" onchange="changedMode()">
                                Android&nbsp;Annotations

                            </label>
                        </div>
                        <!-- controls-row -->
                    </div>
                    <!-- control-group -->

                    <div id="javaSettings">
                        <div class="control-group">
                            <label class="control-label">Java Settings - Scope</label>

                            <div class="controls controls-row">
                                <label class="radio span1">
                                    <input type="radio" name="publicPrivate" id="optionsRadios1"
                                           value="public">
                                    public
                                </label>
                                <label class="radio span1">
                                    <input type="radio" name="publicPrivate" id="optionsRadios2"
                                           value="private" checked>
                                    private

                                </label>

                                <label class="checkbox span1">
                                    <input type="checkbox" name="staticModify" value="static">
                                    static
                                </label>
                                <label class="checkbox span1">
                                    <input type="checkbox" name="finalModify" value="final">
                                    final
                                </label>
                            </div>
                            <!-- controls-row -->
                        </div>
                        <!-- control-group -->
                        <div class="control-group">
                            <label for="prefix" class="control-label">Field&nbsp;Prefix</label>

                            <div class="controls controls-row">

                                <div class="input-append span5">

                                    <input id="prefix" name="prefix" type="text" value="m">
                                    <input type="button" class="btn btn-success" onclick="dealXMLDataWithJava()"
                                           value="Generate">Generate</input>
                                </div>
                            </div>
                            <!-- controls-row -->
                        </div>
                        <!-- control-group -->
                    </div>
                    <!-- java settings -->

                    <div id="aaSettings" class="hidden">
                        <div class="control-group">
                            <label class="control-label"></label>
                            <button class="btn btn-success" onclick="dealXMLDataWithAA()">Generate
                            </button>
                        </div>
                        <!-- controls-row -->
                    </div>

                    <!-- control-group -->

                </div>
                <!--/span 12-->
            </div>
            <!-- row-fluid -->

            <div class="row-fluid">
                <div class="span6">
                    <legend>Layout XML</legend>
                    <textarea class="input-xxlarge" name="xml" rows="16" cols="80" id="xml"
                              placeholder="paste layout XML here"></textarea>
                </div>
                <!-- span 6 -->
                <div class="span6">
                    <legend>Results</legend>
                        <textarea class="input-xxlarge" name="results" rows="15" cols="120" wrap="off" id="results"
                                  placeholder="results appear here"></textarea>
                </div>
                <!-- span 6 -->
            </div>
            <!--/row-->
        </fieldset>
    </form>

    <hr>

    <footer>
        <p>&copy; Lineten Ltd 2013 - We use Google Analytics for web page tracking, this will create a cookie on your
            computer</p>
    </footer>


</div>
<!--/.fluid-container-->

<!-- Le javascript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="/js/jquery.js"></script>
<script src="/js/bootstrap-transition.js"></script>
<script src="/js/bootstrap-alert.js"></script>
<script src="/js/bootstrap-modal.js"></script>
<script src="/js/bootstrap-dropdown.js"></script>
<script src="/js/bootstrap-scrollspy.js"></script>
<script src="/js/bootstrap-tab.js"></script>
<script src="/js/bootstrap-tooltip.js"></script>
<script src="/js/bootstrap-popover.js"></script>
<script src="/js/bootstrap-button.js"></script>
<script src="/js/bootstrap-collapse.js"></script>
<script src="/js/bootstrap-carousel.js"></script>
<script src="/js/bootstrap-typeahead.js"></script>

<script type="text/javascript">

    var _gaq = _gaq || [];
    _gaq.push(['_setAccount', 'UA-38349941-1']);
    _gaq.push(['_trackPageview']);

    (function () {
        var ga = document.createElement('script');
        ga.type = 'text/javascript';
        ga.async = true;
        ga.src = '/js/ga.js';
        var s = document.getElementsByTagName('script')[0];
        s.parentNode.insertBefore(ga, s);
    })();



    //将form中的值转换为键值对。
    function getFormJson(frm) {
        var o = {};
        var a = $(frm).serializeArray();
        $.each(a, function () {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });

        return o;
    }

    function dealXMLDataWithJava() {

        var xml = document.getElementById("xml").value;

        if (xml != null && xml.length > 0) {
            var form = document.getElementById("fomXml");
            var data = getFormJson(form);
            $.ajax({
                type: "POST",
                url: "/dealXml",
                data: data,
                success: function (msg) {
                    document.getElementById("results").innerText = msg;
                }, error: function () {
                    alert("error");
                }
            });
        }

    }


    function dealXMLDataWithAA() {


    }


</script>


</body>
</html>
