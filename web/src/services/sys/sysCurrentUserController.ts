// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 此处后端没有提供注释 GET /api/user/info */
export async function currentUserInfo(options?: { [key: string]: any }) {
  return request<API.RSysUser>('/api/user/info', {
    method: 'GET',
    ...(options || {}),
  });
}
