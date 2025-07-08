import request from '@/utils/request'

// 查询百川分院经营金额列表
export function listJybb(query) {
  return request({
    url: '/system/jybb/list',
    method: 'get',
    params: query
  })
}

// 查询百川分院经营金额详细
export function getJybb(id) {
  return request({
    url: '/system/jybb/' + id,
    method: 'get'
  })
}

// 新增百川分院经营金额
export function addJybb(data) {
  return request({
    url: '/system/jybb',
    method: 'post',
    data: data
  })
}

// 修改百川分院经营金额
export function updateJybb(data) {
  return request({
    url: '/system/jybb',
    method: 'put',
    data: data
  })
}

// 删除百川分院经营金额
export function delJybb(id) {
  return request({
    url: '/system/jybb/' + id,
    method: 'delete'
  })
}
