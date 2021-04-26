import { Role } from "./role";

class User{
    id: number;
    email: string="";
    username: string="";
    password: string="";
    displayName: string="";
    role: Role;
}
export {User}