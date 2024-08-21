// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 POST /api/sys/dict-item/create */
export async function createSysDictItem(
  body: API.SysDictItemCreateInput,
  options?: { [key: string]: any },
) {
  return request<API.RSysDictItemDetailView>('/api/sys/dict-item/create', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /api/sys/dict-item/delete/${param0} */
export async function deleteSysDictItemById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteSysDictItemByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/sys/dict-item/delete/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/dict-item/id/${param0} */
export async function findSysDictItemById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysDictItemByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RSysDictItemDetailView>(`/api/sys/dict-item/id/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/dict-item/list */
export async function findSysDictItemList(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysDictItemListParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysDictItemPageView>('/api/sys/dict-item/list', {
    method: 'GET',
    params: {
      ...params,
      specification: undefined,
      ...params['specification'],
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/dict-item/page */
export async function findSysDictItemPage(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysDictItemPageParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysDictItemPageView>('/api/sys/dict-item/page', {
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

/** 此处后端没有提供注释 POST /api/sys/dict-item/update */
export async function updateSysDictItemById(
  body: API.SysDictItemUpdateInput,
  options?: { [key: string]: any },
) {
  return request<API.RSysDictItemDetailView>('/api/sys/dict-item/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
