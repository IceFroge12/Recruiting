<div class="col-md-2">
    <div id="my_menu" class="collapse navbar-collapse dropdown-menu-left col-md-2 navmenu-fixed-left">
        <div id="left-menu" class="btn-group btn-group-vertical">
            <a href="staff_main" class="btn btn-primary menu-btn left-menu-btn menu-text"><i
                    class="glyphicon glyphicon-home"></i> Main</a>
            <a href="staffStMan.html" class="btn btn-primary menu-btn left-menu-btn menu-text"><i
                    class="glyphicon glyphicon-user"></i> Student management</a>
            <a href="staffSched.html" class="btn btn-primary menu-btn left-menu-btn menu-text"><i
                    class="glyphicon glyphicon-calendar"></i> Schedule</a>
            <a href="staffSettings.html" class="btn btn-primary menu-btn left-menu-btn menu-text"><i
                    class="glyphicon glyphicon-cog"></i> Settings</a>
            <c:if test="${showAdminMenu}">
            <a href="/adminmain" class="btn btn-primary menu-btn left-menu-btn menu-text"><i
                    class="glyphicon glyphicon-home"></i> Main</a>
            <a href="staffmanagement" class="btn btn-primary menu-btn left-menu-btn menu-text"><i
                    class="glyphicon glyphicon-user"></i> Staff management</a>
            <a href="studentmanagement" class="btn btn-primary menu-btn left-menu-btn menu-text"><i
                    class="glyphicon glyphicon-eye-open"></i> Student management</a>
            <a href="scheduling" class="btn btn-primary menu-btn left-menu-btn menu-text"><i
                    class="glyphicon glyphicon-calendar"></i> Scheduling</a>
            <a href="reports" class="btn btn-primary menu-btn left-menu-btn menu-text"><i
                    class="glyphicon glyphicon-list-alt"></i> Reports</a>

            <div id="firstDropdown" class="dropdown width-all ">
                <a href="#"
                   class="dropdown btn btn-primary menu-btn left-menu-btn dropdown-toggle width-all menu-text"
                   data-toggle="dropdown"><i class="glyphicon glyphicon-cog"></i> Settings
                    <b class="caret"></b></a>

                <div class="dropdown-menu navmenu-nav right-col-menu no-padding" role="menu">
                    <a href="formsettings"
                       class="btn btn-primary menu-btn menu-text sub-menu">Forms</a>
                    <a href="adminSettingsRec.html"
                       class="btn btn-primary menu-btn menu-text sub-menu">Recruitment</a>
                    <a href="adminSettingsNot.html"
                       class="btn btn-primary menu-btn menu-text sub-menu">Notifications</a>
                    <a href="adminPersonalSet.html"
                       class="btn btn-primary menu-btn menu-text sub-menu">Personal settings</a>
            </c:if>
        </div>
    </div>
</div>
