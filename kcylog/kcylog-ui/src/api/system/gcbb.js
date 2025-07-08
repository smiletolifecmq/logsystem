import request from '@/utils/request'

// 查询百川工程报表列表
export function listGcbb(query) {
  return request({
    url: '/system/gcbb/list',
    method: 'get',
    params: query
  })
}

// 查询百川工程报表详细
export function getGcbb(id) {
  return request({
    url: '/system/gcbb/' + id,
    method: 'get'
  })
}

// 新增百川工程报表
export function addGcbb(data) {
  return request({
    url: '/system/gcbb',
    method: 'post',
    data: data
  })
}

// 修改百川工程报表
export function updateGcbb(data) {
  return request({
    url: '/system/gcbb',
    method: 'put',
    data: data
  })
}

// 删除百川工程报表
export function delGcbb(id) {
  return request({
    url: '/system/gcbb/' + id,
    method: 'delete'
  })
}

export function getTypeGcbb(query) {
  return request({
    url: '/system/gcbb/getTypeGcbb',
    method: 'get',
    params: query
  })
}
