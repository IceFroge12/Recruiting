<%--
  Created by IntelliJ IDEA.
  User: uakruk
  Date: 5/3/16
  Time: 1:57 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="https://java.sun.com/jsp/jstl/core" %>
        <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>Schedule</title>

                <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
                <link rel="stylesheet" type="text/css" href="css/jasny-bootstrap.min.css">
                <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,400,300,600,700&subset=latin,cyrillic'
                rel='stylesheet' type='text/css'>
                <link rel="stylesheet" type="text/css" href="css/client.css">
                </head>
                <body>
                <%@ include file="staff_header.jsp" %>
                <%@ include file="staff_leftnav.jsp" %>

                <div id="main-block" class="col-md-10 col-md-offset-0 col-sm-9 col-sm-offset-3">
                    <div class="col-md-12 col-sm-12 left-info-col">
                        <div>
                <h4>Choose appropriate variant for each date.</h4>

                <form>
                <div class="row">
                <div class="col-md-3 day-point">
                <h5>20.09.2016 17:00-20:00
                </h5>
                <input type="radio" name="1" value="1"/> Can
                <input type="radio" name="1" value="3"/> Can not<br>
                </div>
                <div class="col-md-3 day-point">
                <h5>21.09.2016 16:00-20:00
                </h5>
                <input type="radio" name="2" value="1"/> Can
                <input type="radio" name="2" value="3"/> Can not<br>
                </div>
                <div class="col-md-3 day-point">
                <h5>22.09.2016 19:00-20:00
                </h5>
                <input type="radio" name="3" value="1"/> Can
                <input type="radio" name="3" value="3"/> Can not<br>
                </div>
                <div class="col-md-3 day-point">
                <h5>23.09.2016 14:00-20:00
                </h5>
                <input type="radio" name="4" value="1"/> Can
                <input type="radio" name="4" value="3"/> Can not<br>
                </div>
                <div class="col-md-3 day-point">
                <h5>24.09.2016 16:00-20:00
                </h5>
                <input type="radio" name="5" value="1"/> Can
                <input type="radio" name="5" value="3"/> Can not<br>
                </div>
                <div class="col-md-3 day-point">
                <h5>25.09.2016 18:00-20:00
                </h5>
                <input type="radio" name="6" value="1"/> Can
                <input type="radio" name="6" value="3"/> Can not<br>
                </div>
                </div>
                <button type="submit" class="btn btn-info search-element btn-left">Confirm</button>
                </form>
                <form>
                <h4>Congratulations!</h4>
                <h4>You are invited to interviews with amazing candidates.</h4>
                <h4>Your unterviews dates: </h4>
                <p class="par">20.09.2017 17:00-20:00</p>
                <p class="par">21.09.2017 17:00-20:00</p>
                <p class="par">24.09.2017 17:00-20:00</p>
                <p class="par">27.09.2017 17:00-20:00</p>
                <p class="par">29.09.2017 17:00-20:00</p>
                <h4>We will be waiting for you at 18 Corps KPI (Street Polytechnic, 41), audience - 420.</h4>
                <h4>See you there!</h4>
                </form>
                </div>
                </div>
                </div>
                <!-- footer -->
                <div id="footer"></div>
                <script type="text/javascript" src="js/jquery.js"></script>
                <script type="text/javascript" src="js/client.js"></script>
                <script type="text/javascript" src="js/bootstrap.js"></script>
                <script type="text/javascript" src="js/jasny-bootstrap.min.js"></script>
                </body>
                </html>
