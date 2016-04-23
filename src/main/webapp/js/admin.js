$(document).ready(function () {



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
                        delegate = "<button class='btn btn-link' " +
                            "type='button'><span class='glyphicon glyphicon-remove'></span></button>";
                    } else {
                        delegate = "<button class='btn btn-info' type='button'>Delegate</button>";
                    }
                    var $tr = $('<tr>').append(
                        $('<td>').text(i),
                        $('<td>').text(item.firstName + " " + item.lastName),
                        $('<td>').text(item.email),
                        $('<td>').text(roleName),
                        $('<td>').append(jQuery(delegate)),

                        $('<td>').append(jQuery("<button class='btn btn-info' type='button' data-toggle='modal' " +
                            "data-target=''#assignedList'>Show Students</button>")),

                        $('<td>').append(jQuery("<button class='btn btn-link' id='ololo'" +
                            " data-toggle='modal' type='button'  data-target=''#editEmployee'>" +
                            "<span class='glyphicon glyphicon-edit'> </span></button>"))
                    ).appendTo("#employee-table");
                    roleName = new String();
                });
            }
        });
    }


    showAllEmployee();


    var admin = false;
    var soft = false;
    var tech = false;
    var user;

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

// get employee prop
    $(".editEmployee").click(function (event) {
        var email = $(this).closest("tr").find('td:eq(2)').text();
        $.ajax({
            url: 'getEmployeeParams',
            type: 'POST',
            dataType: "json",
            data: {"email": email},
            success: function (data) {
                console.log(data);
                // user = data;
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


//Set employee prop
    $("#saveChanges").click(function (event) {
        var firstName = $("#firstNameEdit").val();
        var secondName = $("#secondNameEdit").val();
        var lastName = $("#lastNameEdit").val();
        var email = $("#emailEdit").val();


        user.firstName = firstName;
        user.secondName = secondName;
        user.lastName = lastName;
        user.email = email;
        console.log(user);


        // var user = { email: email, firstName: firstName, secondName: secondName, lastName: lastName,  };

        $.ajax({
            url: 'admin/editEmployee',
            dataType: "json",
            type: 'POST',
            data: user,
            success: function (data) {
                console.log("success edit")
            }
        });
    });


});