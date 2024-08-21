// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 POST /api/sys/department/create */
export async function createSysDepartment(
  body: API.SysDepartmentCreateInput,
  options?: { [key: string]: any },
) {
  return request<API.RSysDepartmentDetailView>('/api/sys/department/create', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /api/sys/department/delete/${param0} */
export async function deleteSysDepartmentById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteSysDepartmentByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/sys/department/delete/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/department/id/${param0} */
export async function findSysDepartmentById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysDepartmentByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RSysDepartmentDetailView>(`/api/sys/department/id/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/department/list */
export async function findSysDepartmentList(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysDepartmentListParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysDepartmentPageView>('/api/sys/department/list', {
    method: 'GET',
    params: {
      ...params,
      specification: undefined,
      ...params['specification'],
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/department/page */
export async function findSysDepartmentPage(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysDepartmentPageParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysDepartmentPageView>('/api/sys/department/page', {
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

/** 此处后端没有提供注释 GET /api/sys/department/tree */
export async function findSysDepartmentTree(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysDepartmentTreeParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysDepartmentTreeView>('/api/sys/department/tree', {
    method: 'GET',
    params: {
      ...params,
      specification: undefined,
      ...params['specification'],
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/department/tree-root */
export async function findSysDepartmentTreeRoot(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysDepartmentTreeRootParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysDepartmentTreeRootView>('/api/sys/department/tree-root', {
    method: 'GET',
    params: {
      ...params,
      specification: undefined,
      ...params['specification'],
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 POST /api/sys/department/update */
export async function updateSysDepartmentById(
  body: API.SysDepartmentUpdateInput,
  options?: { [key: string]: any },
) {
  return request<API.RSysDepartmentDetailView>('/api/sys/department/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
