import request from '@/utils/request'

// 查询客户调查满意度列表
export function listKhdcmyd(query) {
  return request({
    url: '/system/khdcmyd/list',
    method: 'get',
    params: query
  })
}

// 查询客户调查满意度详细
export function getKhdcmyd(id) {
  return request({
    url: '/system/khdcmyd/' + id,
    method: 'get'
  })
}

// 新增客户调查满意度
export function addKhdcmyd(data) {
  return request({
    url: '/system/khdcmyd',
    method: 'post',
    data: data
  })
}

// 修改客户调查满意度
export function updateKhdcmyd(data) {
  return request({
    url: '/system/khdcmyd',
    method: 'put',
    data: data
  })
}

// 删除客户调查满意度
export function delKhdcmyd(id) {
  return request({
    url: '/system/khdcmyd/' + id,
    method: 'delete'
  })
}
