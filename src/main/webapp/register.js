 function validateForm() {
            var inputs = document.getElementsByTagName("input");

            for (var i = 0; i < inputs.length; i++) {
                if (inputs[i].type === "text" || inputs[i].type === "password") {
                    if (inputs[i].value === "") {
                        alert("Please fill out all fields");
                        return false;
                    }
                }
            }

            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("confirmPassword").value;
            var passwordError = document.getElementById("passwordError");

            if (password !== confirmPassword) {
                passwordError.style.display = "block";
                return false;
            }

            passwordError.style.display = "none";
            return true;
        }

        function clearPasswordError() {
            var passwordError = document.getElementById("passwordError");
            passwordError.style.display = "none";
        }