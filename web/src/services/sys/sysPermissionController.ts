// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 POST /api/sys/permission/create */
export async function createSysPermission(
  body: API.SysPermissionCreateInput,
  options?: { [key: string]: any },
) {
  return request<API.RSysPermissionDetailView>('/api/sys/permission/create', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /api/sys/permission/delete/${param0} */
export async function deleteSysPermissionById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteSysPermissionByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/sys/permission/delete/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/permission/id/${param0} */
export async function findSysPermissionById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysPermissionByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RSysPermissionDetailView>(`/api/sys/permission/id/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/permission/list */
export async function findSysPermissionList(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysPermissionListParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysPermissionPageView>('/api/sys/permission/list', {
    method: 'GET',
    params: {
      ...params,
      specification: undefined,
      ...params['specification'],
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/permission/page */
export async function findSysPermissionPage(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysPermissionPageParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysPermissionPageView>('/api/sys/permission/page', {
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

/** 此处后端没有提供注释 GET /api/sys/permission/tree */
export async function findSysPermissionTree(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysPermissionTreeParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysPermissionTreeView>('/api/sys/permission/tree', {
    method: 'GET',
    params: {
      ...params,
      specification: undefined,
      ...params['specification'],
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/permission/tree-root */
export async function findSysPermissionTreeRoot(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysPermissionTreeRootParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysPermissionTreeRootView>('/api/sys/permission/tree-root', {
    method: 'GET',
    params: {
      ...params,
      specification: undefined,
      ...params['specification'],
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/sys/permission/update */
export async function updateSysPermissionById(
  body: API.SysPermissionUpdateInput,
  options?: { [key: string]: any },
) {
  return request<API.RSysPermissionDetailView>('/api/sys/permission/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
