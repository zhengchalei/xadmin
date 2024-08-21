// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 POST /api/sys/role/create */
export async function createSysRole(
  body: API.SysRoleCreateInput,
  options?: { [key: string]: any },
) {
  return request<API.RSysRoleDetailView>('/api/sys/role/create', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /api/sys/role/delete/${param0} */
export async function deleteSysRoleById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteSysRoleByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/sys/role/delete/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/role/id/${param0} */
export async function findSysRoleById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysRoleByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RSysRoleDetailView>(`/api/sys/role/id/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/role/list */
export async function findSysRoleList(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysRoleListParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysRolePageView>('/api/sys/role/list', {
    method: 'GET',
    params: {
      ...params,
      specification: undefined,
      ...params['specification'],
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/role/page */
export async function findSysRolePage(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysRolePageParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysRolePageView>('/api/sys/role/page', {
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

/** 此处后端没有提供注释 POST /api/sys/role/update */
export async function updateSysRoleById(
  body: API.SysRoleUpdateInput,
  options?: { [key: string]: any },
) {
  return request<API.RSysRoleDetailView>('/api/sys/role/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
