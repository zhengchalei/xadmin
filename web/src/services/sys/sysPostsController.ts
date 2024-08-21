// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 POST /api/sys/posts/create */
export async function createSysPosts(
  body: API.SysPostsCreateInput,
  options?: { [key: string]: any },
) {
  return request<API.RSysPostsDetailView>('/api/sys/posts/create', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 DELETE /api/sys/posts/delete/${param0} */
export async function deleteSysPostsById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteSysPostsByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/sys/posts/delete/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/posts/id/${param0} */
export async function findSysPostsById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysPostsByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RSysPostsDetailView>(`/api/sys/posts/id/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/posts/list */
export async function findSysPostsList(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysPostsListParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysPostsPageView>('/api/sys/posts/list', {
    method: 'GET',
    params: {
      ...params,
      specification: undefined,
      ...params['specification'],
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/posts/page */
export async function findSysPostsPage(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysPostsPageParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysPostsPageView>('/api/sys/posts/page', {
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

/** 此处后端没有提供注释 POST /api/sys/posts/update */
export async function updateSysPostsById(
  body: API.SysPostsUpdateInput,
  options?: { [key: string]: any },
) {
  return request<API.RSysPostsDetailView>('/api/sys/posts/update', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}
