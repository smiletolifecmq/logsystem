import request from '@/utils/request'

// 查询类型配置表列表
export function listType(query) {
  return request({
    url: '/system/geoType/list',
    method: 'get',
    params: query
  })
}

// 查询类型配置表详细
export function getType(typeId) {
  return request({
    url: '/system/geoType/' + typeId,
    method: 'get'
  })
}

// 新增类型配置表
export function addType(data) {
  return request({
    url: '/system/geoType',
    method: 'post',
    data: data
  })
}

// 修改类型配置表
export function updateType(data) {
  return request({
    url: '/system/geoType',
    method: 'put',
    data: data
  })
}

// 删除类型配置表
export function delType(typeId) {
  return request({
    url: '/system/geoType/' + typeId,
    method: 'delete'
  })
}
