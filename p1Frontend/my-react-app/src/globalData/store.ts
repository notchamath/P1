import { UserInterface } from "../interfaces/UserInterface";

export const store: any = {
    //we typically want to store user session info on the front end
    //...for the sake of personalization, role-based behavior, and easier HTTP requests
    loggedInUser: {
        userId: -1,
        firstName: "",
        lastName: "",
        username: "",
        role: ""
    } as UserInterface, 

    //for instance, we could have stored the base URL to our backend
    baseURL: "http://localhost:4444/"
}
