import request from '@/utils/request'

// 查询责权发生制列表
export function listResponsibilities(query) {
  return request({
    url: '/system/responsibilities/list',
    method: 'get',
    params: query
  })
}

// 查询责权发生制详细
export function getResponsibilities(zqId) {
  return request({
    url: '/system/responsibilities/' + zqId,
    method: 'get'
  })
}

// 新增责权发生制
export function addResponsibilities(data) {
  return request({
    url: '/system/responsibilities',
    method: 'post',
    data: data
  })
}

// 修改责权发生制
export function updateResponsibilities(data) {
  return request({
    url: '/system/responsibilities',
    method: 'put',
    data: data
  })
}

// 删除责权发生制
export function delResponsibilities(zqId) {
  return request({
    url: '/system/responsibilities/' + zqId,
    method: 'delete'
  })
}

export function getResponsibilitiesInfo(zqId) {
  return request({
    url: '/system/responsibilities/info/' + zqId,
    method: 'get'
  })
}
