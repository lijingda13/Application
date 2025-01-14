export const Url = {
    login_post : "/api/auth/login", // login
    logout_post : "/logout", // logout

    users_post : "/api/users", // register a user
    user_id_get : (id:any) => `/api/users/${id}`, // get user by id
    user_id_patch: (id:any) => `/api/users/${id}`, // update user by id

    projects_staff_get: (id: any) => `/api/staff/${id}/proposed-projects`, // get staff's projects.
    projects_student_available_get: (id: any) => `/api/students/${id}/available-projects`, // get available projects.
    projects_student_assigned_get: (id: any) => `/api/users/${id}/student-info`, // get student's assigned projects .
    projects_id_get: (id: any) => `/projects/${id}`, // get project by project id.
    projects_create_uesrid_post: (staffId: any) => `/api/staff/${staffId}/create-project`, // create a project
    projects_id_assign_post: (projectId: any) => `/api/projects/${projectId}/assign-project`, // asign a project to a student

    projects_id_registerinterest_post: (projectId: any) => `/api/projects/${projectId}/register-interest`,  // student register interest
    projects_id_unregisterinterest_post: (projectId: any) => `/api/projects/${projectId}/unregister-interest`,  // student cancel interest
    project_id_delete: (projectId: any) => `/api/projects/${projectId}`, // staff delete project by id
}
