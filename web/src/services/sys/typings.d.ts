declare namespace API {
  type deleteSysDepartmentByIdParams = {
    id: number;
  };

  type deleteSysDictByIdParams = {
    id: number;
  };

  type deleteSysDictItemByIdParams = {
    id: number;
  };

  type deleteSysLoginLogByIdParams = {
    id: number;
  };

  type deleteSysPermissionByIdParams = {
    id: number;
  };

  type deleteSysPostsByIdParams = {
    id: number;
  };

  type deleteSysRoleByIdParams = {
    id: number;
  };

  type deleteSysUserPageByIdParams = {
    id: number;
  };

  type findSysDepartmentByIdParams = {
    id: number;
  };

  type findSysDepartmentListParams = {
    specification: SysDepartmentPageSpecification;
  };

  type findSysDepartmentPageParams = {
    specification: SysDepartmentPageSpecification;
    pageable: QueryPage;
  };

  type findSysDepartmentTreeParams = {
    specification: SysDepartmentPageSpecification;
  };

  type findSysDepartmentTreeRootParams = {
    specification: SysDepartmentPageSpecification;
  };

  type findSysDictByIdParams = {
    id: number;
  };

  type findSysDictItemByIdParams = {
    id: number;
  };

  type findSysDictItemListParams = {
    specification: SysDictItemPageSpecification;
  };

  type findSysDictItemPageParams = {
    specification: SysDictItemPageSpecification;
    pageable: QueryPage;
  };

  type findSysDictListParams = {
    specification: SysDictPageSpecification;
  };

  type findSysDictPageParams = {
    specification: SysDictPageSpecification;
    pageable: QueryPage;
  };

  type findSysLoginLogByIdParams = {
    id: number;
  };

  type findSysLoginLogListParams = {
    specification: SysLoginLogPageSpecification;
  };

  type findSysLoginLogPageParams = {
    specification: SysLoginLogPageSpecification;
    pageable: QueryPage;
  };

  type findSysPermissionByIdParams = {
    id: number;
  };

  type findSysPermissionListParams = {
    specification: SysPermissionPageSpecification;
  };

  type findSysPermissionPageParams = {
    specification: SysPermissionPageSpecification;
    pageable: QueryPage;
  };

  type findSysPermissionTreeParams = {
    specification: SysPermissionPageSpecification;
  };

  type findSysPermissionTreeRootParams = {
    specification: SysPermissionPageSpecification;
  };

  type findSysPostsByIdParams = {
    id: number;
  };

  type findSysPostsListParams = {
    specification: SysPostsPageSpecification;
  };

  type findSysPostsPageParams = {
    specification: SysPostsPageSpecification;
    pageable: QueryPage;
  };

  type findSysRoleByIdParams = {
    id: number;
  };

  type findSysRoleListParams = {
    specification: SysRolePageSpecification;
  };

  type findSysRolePageParams = {
    specification: SysRolePageSpecification;
    pageable: QueryPage;
  };

  type findSysUserByIdParams = {
    id: number;
  };

  type findSysUserListParams = {
    specification: SysUserPageSpecification;
  };

  type findSysUserPageParams = {
    specification: SysUserPageSpecification;
    pageable: QueryPage;
  };

  type LoginDTO = {
    username: string;
    password: string;
    tenant: string;
    captcha: string;
  };

  type LoginResponse = {
    username: string;
    accessToken: string;
    refreshToken: string;
  };

  type QueryPage = {
    currentPage: number;
    pageSize: number;
    sortDirection: 'ASC' | 'DESC';
    sortProperty: string;
  };

  type RBoolean = {
    data?: boolean;
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RListSysDepartmentPageView = {
    data?: SysDepartmentPageView[];
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RListSysDepartmentTreeRootView = {
    data?: SysDepartmentTreeRootView[];
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RListSysDepartmentTreeView = {
    data?: SysDepartmentTreeView[];
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RListSysDictItemPageView = {
    data?: SysDictItemPageView[];
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RListSysDictPageView = {
    data?: SysDictPageView[];
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RListSysLoginLogPageView = {
    data?: SysLoginLogPageView[];
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RListSysPermissionPageView = {
    data?: SysPermissionPageView[];
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RListSysPermissionTreeRootView = {
    data?: SysPermissionTreeRootView[];
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RListSysPermissionTreeView = {
    data?: SysPermissionTreeView[];
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RListSysPostsPageView = {
    data?: SysPostsPageView[];
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RListSysRolePageView = {
    data?: SysRolePageView[];
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RListSysUserPageView = {
    data?: SysUserPageView[];
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RLoginResponse = {
    data?: LoginResponse;
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RSysDepartmentDetailView = {
    data?: SysDepartmentDetailView;
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RSysDictDetailView = {
    data?: SysDictDetailView;
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RSysDictItemDetailView = {
    data?: SysDictItemDetailView;
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RSysLoginLogDetailView = {
    data?: SysLoginLogDetailView;
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RSysPermissionDetailView = {
    data?: SysPermissionDetailView;
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RSysPostsDetailView = {
    data?: SysPostsDetailView;
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RSysRoleDetailView = {
    data?: SysRoleDetailView;
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RSysUser = {
    data?: SysUser;
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type RSysUserDetailView = {
    data?: SysUserDetailView;
    success: boolean;
    errorMessage?: string;
    code: number;
    total: number;
  };

  type switchTenantParams = {
    tenant: string;
  };

  type SysDepartment = {
    description?: string;
    name: string;
    parent?: SysDepartment;
    id: number;
    sort: number;
    children: SysDepartment[];
    status: boolean;
    tenant: string;
    createTime: string;
    updateTime: string;
  };

  type SysDepartmentCreateInput = {
    name: string;
    sort: number;
    status: boolean;
    parentId?: number;
    description?: string;
  };

  type SysDepartmentDetailView = {
    id: number;
    tenant: string;
    createTime: string;
    updateTime: string;
    name: string;
    sort: number;
    status: boolean;
    description?: string;
  };

  type SysDepartmentPageSpecification = {
    name?: string;
    status?: boolean;
    description?: string;
    parentId?: number;
    startTime?: string;
    endTime?: string;
  };

  type SysDepartmentPageView = {
    id: number;
    tenant: string;
    createTime: string;
    updateTime: string;
    name: string;
    sort: number;
    status: boolean;
    description?: string;
  };

  type SysDepartmentTreeRootView = {
    id: number;
    tenant: string;
    createTime: string;
    updateTime: string;
    name: string;
    sort: number;
    status: boolean;
    description?: string;
  };

  type SysDepartmentTreeView = {
    id: number;
    tenant: string;
    createTime: string;
    updateTime: string;
    name: string;
    sort: number;
    status: boolean;
    description?: string;
  };

  type SysDepartmentUpdateInput = {
    id: number;
    name: string;
    sort: number;
    status: boolean;
    parentId?: number;
    description?: string;
  };

  type SysDictCreateInput = {
    name: string;
    code: string;
    sort: number;
    status: boolean;
    description?: string;
  };

  type SysDictDetailView = {
    id: number;
    name: string;
    code: string;
    sort: number;
    description?: string;
    status: boolean;
    dictItems: TargetOfDictItems[];
  };

  type SysDictItemCreateInput = {
    name: string;
    code: string;
    data: string;
    sort: number;
    status: boolean;
    description?: string;
    dictId: number;
  };

  type SysDictItemDetailView = {
    id: number;
    name: string;
    code: string;
    data: string;
    sort: number;
    description?: string;
    status: boolean;
    dict: TargetOfDict;
  };

  type SysDictItemPageSpecification = {
    name?: string;
    code?: string;
    data?: string;
    description?: string;
    status?: boolean;
    dictId?: number;
  };

  type SysDictItemPageView = {
    id: number;
    name: string;
    code: string;
    data: string;
    sort: number;
    description?: string;
    status: boolean;
    dict: TargetOfDict;
  };

  type SysDictItemUpdateInput = {
    id: number;
    name: string;
    code: string;
    data: string;
    sort: number;
    status: boolean;
    description?: string;
    dictId: number;
  };

  type SysDictPageSpecification = {
    name?: string;
    code?: string;
    description?: string;
    status?: boolean;
  };

  type SysDictPageView = {
    id: number;
    name: string;
    code: string;
    sort: number;
    description?: string;
    status: boolean;
  };

  type SysDictUpdateInput = {
    id: number;
    name: string;
    code: string;
    sort: number;
    status: boolean;
    description?: string;
  };

  type SysLoginLogDetailView = {
    id: number;
    username: string;
    password: string;
    ip: string;
    address?: string;
    loginTime: string;
    status: boolean;
    errorMessage?: string;
    tenant: string;
  };

  type SysLoginLogPageSpecification = {
    username?: string;
    password?: string;
    ip?: string;
    address?: string;
    status?: boolean;
    tenant?: string;
    errorMessage?: string;
    startTime?: string;
    endTime?: string;
  };

  type SysLoginLogPageView = {
    id: number;
    username: string;
    password: string;
    ip: string;
    address?: string;
    loginTime: string;
    status: boolean;
    errorMessage?: string;
    tenant: string;
  };

  type SysPermission = {
    description?: string;
    name: string;
    parent?: SysPermission;
    id: number;
    code: string;
  };

  type SysPermissionCreateInput = {
    name: string;
    code: string;
    parentId?: number;
    description?: string;
  };

  type SysPermissionDetailView = {
    id: number;
    name: string;
    code: string;
    description?: string;
  };

  type SysPermissionPageSpecification = {
    name?: string;
    code?: string;
    description?: string;
    parentId?: number;
  };

  type SysPermissionPageView = {
    id: number;
    name: string;
    code: string;
    description?: string;
  };

  type SysPermissionTreeRootView = {
    id: number;
    name: string;
    code: string;
    description?: string;
  };

  type SysPermissionTreeView = {
    id: number;
    name: string;
    code: string;
    description?: string;
  };

  type SysPermissionUpdateInput = {
    id: number;
    name: string;
    code: string;
    parentId?: number;
    description?: string;
  };

  type SysPosts = {
    description?: string;
    name: string;
    id: number;
    sort: number;
    status: boolean;
    tenant: string;
    createTime: string;
    updateTime: string;
  };

  type SysPostsCreateInput = {
    name: string;
    sort: number;
    status: boolean;
    description?: string;
  };

  type SysPostsDetailView = {
    id: number;
    tenant: string;
    createTime: string;
    updateTime: string;
    name: string;
    sort: number;
    status: boolean;
    description?: string;
  };

  type SysPostsPageSpecification = {
    name?: string;
    status?: boolean;
    description?: string;
    startTime?: string;
    endTime?: string;
  };

  type SysPostsPageView = {
    id: number;
    tenant: string;
    createTime: string;
    updateTime: string;
    name: string;
    sort: number;
    status: boolean;
    description?: string;
  };

  type SysPostsUpdateInput = {
    id: number;
    name: string;
    sort: number;
    status: boolean;
    description?: string;
  };

  type SysRole = {
    description?: string;
    name: string;
    permissions: SysPermission[];
    id: number;
    code: string;
    tenant: string;
    createTime: string;
    updateTime: string;
  };

  type SysRoleCreateInput = {
    name: string;
    code: string;
    description?: string;
    permissionIds: number[];
  };

  type SysRoleDetailView = {
    id: number;
    tenant: string;
    createTime: string;
    updateTime: string;
    name: string;
    code: string;
    description?: string;
    permissions: TargetOfPermissions[];
  };

  type SysRolePageSpecification = {
    name?: string;
    code?: string;
    description?: string;
    startTime?: string;
    endTime?: string;
    id?: number;
  };

  type SysRolePageView = {
    id: number;
    tenant: string;
    createTime: string;
    updateTime: string;
    name: string;
    code: string;
    description?: string;
    permissions: TargetOfPermissions[];
  };

  type SysRoleUpdateInput = {
    id: number;
    name: string;
    code: string;
    description?: string;
    permissionIds: number[];
  };

  type SysUser = {
    id: number;
    roles: SysRole[];
    status: boolean;
    username: string;
    department?: SysDepartment;
    phoneNumber?: string;
    birthday?: string;
    avatar?: string;
    gender?: 'MALE' | 'FEMALE';
    email: string;
    posts?: SysPosts;
    tenant: string;
    createTime: string;
    updateTime: string;
  };

  type SysUserCreateInput = {
    username: string;
    email: string;
    gender?: 'MALE' | 'FEMALE';
    phoneNumber?: string;
    birthday?: string;
    departmentId?: number;
    postsId?: number;
    status: boolean;
    roleIds: number[];
  };

  type SysUserDetailView = {
    id: number;
    tenant: string;
    createTime: string;
    updateTime: string;
    username: string;
    email: string;
    avatar?: string;
    status: boolean;
    phoneNumber?: string;
    gender?: 'MALE' | 'FEMALE';
    birthday?: string;
    roles: TargetOfRoles[];
    department?: TargetOfDepartment;
    posts?: TargetOfPosts;
  };

  type SysUserPageSpecification = {
    username?: string;
    email?: string;
    status?: boolean;
    phoneNumber?: string;
    gender?: 'MALE' | 'FEMALE';
    departmentId?: number;
    postsId?: number;
    startTime?: string;
    endTime?: string;
    id?: number;
  };

  type SysUserPageView = {
    id: number;
    tenant: string;
    createTime: string;
    updateTime: string;
    username: string;
    email: string;
    avatar?: string;
    status: boolean;
    phoneNumber?: string;
    gender?: 'MALE' | 'FEMALE';
    birthday?: string;
    roles: TargetOfRoles[];
    department?: TargetOfDepartment;
    posts?: TargetOfPosts;
  };

  type SysUserUpdateInput = {
    id: number;
    username: string;
    email: string;
    gender?: 'MALE' | 'FEMALE';
    phoneNumber?: string;
    birthday?: string;
    departmentId?: number;
    postsId?: number;
    status: boolean;
    roleIds: number[];
  };

  type TargetOfDepartment = {
    id: number;
    tenant: string;
    createTime: string;
    updateTime: string;
    name: string;
    sort: number;
    status: boolean;
    description?: string;
  };

  type TargetOfDict = {
    id: number;
    name: string;
    code: string;
    sort: number;
    description?: string;
    status: boolean;
  };

  type TargetOfDictItems = {
    id: number;
    name: string;
    code: string;
    data: string;
    sort: number;
    description?: string;
    status: boolean;
  };

  type TargetOfPermissions = {
    id: number;
    name: string;
    code: string;
    description?: string;
  };

  type TargetOfPosts = {
    id: number;
    tenant: string;
    createTime: string;
    updateTime: string;
    name: string;
    sort: number;
    status: boolean;
    description?: string;
  };

  type TargetOfRoles = {
    id: number;
    tenant: string;
    createTime: string;
    updateTime: string;
    name: string;
    code: string;
    description?: string;
    permissions: TargetOfPermissions[];
  };
}
