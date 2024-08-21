// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 POST /api/sys/user/create */
export async function createSysUser(
  body: API.SysUserCreateInput,
  options?: { [key: string]: any },
) {
  return request<API.RSysUserDetailView>('/api/sys/user/create', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /api/sys/user/delete/${param0} */
export async function deleteSysUserPageById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteSysUserPageByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/sys/user/delete/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/user/id/${param0} */
export async function findSysUserById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysUserByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RSysUserDetailView>(`/api/sys/user/id/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/user/list */
export async function findSysUserList(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysUserListParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysUserPageView>('/api/sys/user/list', {
    method: 'GET',
    params: {
      ...params,
      specification: undefined,
      ...params['specification'],
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/user/page */
export async function findSysUserPage(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysUserPageParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysUserPageView>('/api/sys/user/page', {
    method: 'GET',
    params: {
      ...params,
      specification: undefined,
      ...params['specification'],
      pageable: undefined,
      ...params['pageable'],
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/sys/user/update */
export async function updateSysUserPageById(
  body: API.SysUserUpdateInput,
  options?: { [key: string]: any },
) {
  return request<API.RSysUserDetailView>('/api/sys/user/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
