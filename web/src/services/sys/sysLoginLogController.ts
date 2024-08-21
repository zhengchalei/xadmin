// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 DELETE /api/sys/login-log/delete/${param0} */
export async function deleteSysLoginLogById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteSysLoginLogByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RBoolean>(`/api/sys/login-log/delete/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/login-log/id/${param0} */
export async function findSysLoginLogById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysLoginLogByIdParams,
  options?: { [key: string]: any },
) {
  const { id: param0, ...queryParams } = params;
  return request<API.RSysLoginLogDetailView>(`/api/sys/login-log/id/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/login-log/list */
export async function findSysLoginLogList(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysLoginLogListParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysLoginLogPageView>('/api/sys/login-log/list', {
    method: 'GET',
    params: {
      ...params,
      specification: undefined,
      ...params['specification'],
    },
    ...(options || {}),
  });
}

/** 此处后端没有提供注释 GET /api/sys/login-log/page */
export async function findSysLoginLogPage(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.findSysLoginLogPageParams,
  options?: { [key: string]: any },
) {
  return request<API.RListSysLoginLogPageView>('/api/sys/login-log/page', {
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
