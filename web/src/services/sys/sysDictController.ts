// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 POST /api/sys/dict/create */
export async function createSysDict(
  body: API.SysDictCreateInput,
  options?: { [key: string]: any },
) {
  return request<API.RSysDictDetailView>('/api/sys/dict/create', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /api/sys/dict/delete/${param0} */
export async function deleteSysDictById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteSysDictByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/sys/dict/delete/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/dict/id/${param0} */
export async function findSysDictById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysDictByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RSysDictDetailView>(`/api/sys/dict/id/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/dict/list */
export async function findSysDictList(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysDictListParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysDictPageView>('/api/sys/dict/list', {
    method: 'GET',
    params: {
      ...params,
      specification: undefined,
      ...params['specification'],
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/dict/page */
export async function findSysDictPage(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysDictPageParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysDictPageView>('/api/sys/dict/page', {
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

/** 此处后端没有提供注释 POST /api/sys/dict/update */
export async function updateSysDictById(
  body: API.SysDictUpdateInput,
  options?: { [key: string]: any },
) {
  return request<API.RSysDictDetailView>('/api/sys/dict/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
