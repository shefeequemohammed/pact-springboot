# PACT - Springboot

## pact-student-service-provider

This project exposes two API's
1) "/student/{id}" which will give a response in the following Json format:-

                {
                    "id": "1",
                    "firstName": "Steve",
                    "lastName": "Waugh",
                    "age": 21
                 }

Response is hard coded as follows:=

        if(id.equals("1"))
             return new Student(id, "Steve", "Waugh", 21);
        else if(id.equals("2"))
             return  new Student(id, "Brian", "Lara", 22);
        else
             return  new Student(id, "Rahul", "Dravid", 23);

2) "/department/{id}" which will give a response as follows:-

             {
                "id": "1",
                "name": "Dep 1",
                "students": [
                        {
                          "id": "1",
                          "firstName": "John",
                          "lastName": "Smith",
                          "age": 23
                         },
                         {
                          "id": "2",
                          "firstName": "Adam",
                          "lastName": "Zamba",
                          "age": 21
                         }
                            ]
               }
The response has a student array in it.

