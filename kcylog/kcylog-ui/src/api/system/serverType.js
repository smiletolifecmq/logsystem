import request from '@/utils/request'

// 查询内部服务类型表列表
export function listType() {
  return request({
    url: '/system/serverType/list',
    method: 'get',
  })
}

// 查询内部服务类型表详细
export function getType(id) {
  return request({
    url: '/system/serverType/' + id,
    method: 'get'
  })
}

// 新增内部服务类型表
export function addType(data) {
  return request({
    url: '/system/serverType',
    method: 'post',
    data: data
  })
}

// 修改内部服务类型表
export function updateType(data) {
  return request({
    url: '/system/serverType',
    method: 'put',
    data: data
  })
}

// 删除内部服务类型表
export function delType(id) {
  return request({
    url: '/system/serverType/' + id,
    method: 'delete'
  })
}
