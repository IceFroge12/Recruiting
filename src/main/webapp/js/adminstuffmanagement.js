$(document).ready(function () {


    var admin = false;
    var soft = false;
    var tech = false;
    var adminEdit = false;
    var softEdit = false;
    var techEdit = false;

    $("body").on("click", "#status", function () {
        $('#assignedList').bPopup({
            position: [300, 300],
            follow: [true, false],
        });


        var email = $(this).closest("tr").find('td:eq(2)').text();
        console.log(email);
        $.ajax({
            url: 'showAssignedStudent',
            type: 'GET',
            dataType: "json",
            data: {"email": email},
            success: function (data) {
                console.log(data);
            }
        });
        
    });


    $("body").on("click", "#popup", function () {
        $('#editEmployee').bPopup({
            position: [300, 300],
            follow: [true, false],
        });

        var email = $(this).closest("tr").find('td:eq(2)').text();
        console.log(email);
        $.ajax({
            url: 'getEmployeeParams',
            type: 'POST',
            dataType: "json",
            data: {"email": email},
            success: function (data) {
                console.log(data);
                user = data;
                $("#firstNameEdit").val(data.firstName);
                $("#secondNameEdit").val(data.secondName);
                $("#lastNameEdit").val(data.lastName);
                $("#emailEdit").val(data.email);
                $.each(data.roles, function (key, val) {
                    if (val.roleName == "ROLE_ADMIN") {
                        $("#adminEdit").prop("checked", true);
                    }
                    if (val.roleName == "ROLE_TECH") {
                        $("#techEdit").prop("checked", true);
                    }
                    if (val.roleName == "ROLE_SOFT") {
                        $("#softEdit").prop("checked", true);
                    }
                });
            }
        });

    });


    $("body").on("click", "#delegate", function () {
        var email = $(this).closest("tr").find('td:eq(2)').text();
        var status;
        $.ajax({
            url: 'changeEmployeeStatus',
            type: 'GET',
            data: {"email": email},
            success: function (data) {
                console.log(data)
                status = data;
            }
        });
        if (data = true) {
            $(this).closest("tr").find('td:eq(4)').replaceWith("<td><button class='btn btn-info' " +
                "type='button' id='delegate'><span class='glyphicon glyphicon-remove'></span></button></td>");
        }else {
            $(this).closest("tr").find('td:eq(4)').replaceWith("<td><button class='btn btn-info' " +
                "id='delegate' type='button'></button></td>");
        }
    });


    //show employees table
    function showAllEmployee() {
        $.ajax({
            url: 'showAllEmployee',
            type: 'POST',
            success: function (data) {
                console.log("save Employee")
                console.log(data)
                var roleName = new String();
                var delegate = new String();
                $.each(data, function (i, item) {
                    $.each(item.roles, function (key, val) {
                        console.log(val.roleName);
                        roleName += " " + val.roleName + " ";
                    })
                    if (item.active == true) {
                        delegate = "<button class='btn btn-info' " +
                            "type='button' id='delegate'><span class='glyphicon glyphicon-remove'></span></button>";
                    } else {
                        delegate = "<button class='btn btn-info' id='delegate' type='button'></button>";
                    }
                    var $tr = $('<tr>').append(
                        $('<td>').text(i),
                        $('<td>').text(item.firstName + " " + item.lastName),
                        $('<td>').text(item.email),
                        $('<td>').text(roleName),
                        $('<td>').append(jQuery(delegate)),

                        $('<td>').append(jQuery("<button class='btn btn-info' type='button' id='status' data-toggle='modal' " +
                            "data-target=''#assignedList'>Show Students</button>")),

                        $('<td>').append(jQuery("<button class='btn btn-link' id='popup'" +
                            " data-toggle='modal' type='button'  data-target=''#editEmployee'>" +
                            "<span class='glyphicon glyphicon-edit'> </span></button>"))
                    ).appendTo("#employee-table");
                    roleName = new String();
                });
            }
        });
    }


    showAllEmployee();


    function addRemoveRoles() {

        $("#tech").on("click", function () {
            if ($(this).is(":checked")) {
                tech = true;
            }
            else {
                tech = false;
            }
        });

        $("#soft").on("click", function () {
            if ($(this).is(":checked")) {
                soft = true
            }
            else {
                soft = false;
            }
        });

        $("#admin").on("click", function () {
            if ($(this).is(":checked")) {
                admin = true;
            }
            else {
                admin = false;
            }
        });
    }

    addRemoveRoles();


    $("#saveEmployee").click(function (event) {

        var firstName = $("#firstName").val();
        var secondName = $("#secondName").val();
        var lastName = $("#lastName").val();
        var email = $("#email").val();


        $.ajax({
            url: 'addEmployee',
            type: 'POST',
            data: {
                "firstName": firstName, "secondName": secondName, "lastName": lastName,
                "email": email, "admin": admin, "soft": soft, "tech": tech
            },
            success: function (data) {
                console.log("save Employee")

            }
        });
    });


    function editRoles() {

        $("#techEdit").on("click", function () {
            if ($(this).is(":checked")) {
                techEdit = true;
            }
            else {
                techEdit = false;
            }
        });

        $("#softEdit").on("click", function () {
            if ($(this).is(":checked")) {
                softEdit = true
            }
            else {
                softEdit = false;
            }
        });

        $("#adminedit").on("click", function () {
            if ($(this).is(":checked")) {
                adminEdit = true;
            }
            else {
                adminEdit = false;
            }
        });
    }

    editRoles();


    //Set employee prop
    $("#saveChanges").click(function (event) {
        var firstName = $("#firstNameEdit").val();
        var secondName = $("#secondNameEdit").val();
        var lastName = $("#lastNameEdit").val();
        var email = $("#emailEdit").val();


        var user = {
            email: email, firstName: firstName, secondName: secondName,
            lastName: lastName, admin: adminEdit, soft: softEdit, tech: techEdit
        };
        console.log(user);

        $.ajax({
            url: 'editEmployee',
            dataType: "json",
            type: 'POST',
            data: user,
            success: function (data) {
                console.log("success edit")
            }
        });
    });


});