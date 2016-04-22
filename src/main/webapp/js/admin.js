$(document).ready(function () {

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

    $("#addEmployee").click(function (event) {

        var firstName = $("#firstName").val();
        var secondName = $("#secondName").val();
        var lastName = $("#lastName").val();
        var email = $("#email").val();


        $.ajax({
            url: 'admin/addEmployee',
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
            url: 'admin/getEmployeeParams',
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


    //Set employee prop
    $("#saveChanges").click(function (event) {
        var firstName = $("#firstNameEdit").val();
        var secondName = $("#secondNameEdit").val();
        var lastName = $("#lastNameEdit").val();
        var email = $("#emailEdit").val();
        

        var user = { email: email, firstName: firstName, secondName: secondName, lastName: lastName};
      

        $.ajax({
            url: 'admin/editEmployee',
            dataType : "json",
            type: 'POST',
            data: user,
            success: function (data) {
                console.log("success edit")
            }
        });


    });

});