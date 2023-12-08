import request from '@/utils/request'

// 查询评定表列表
export function listAssess(query) {
  return request({
    url: '/system/geoAssess/list',
    method: 'get',
    params: query
  })
}

// 查询评定表详细
export function getAssess(assessId) {
  return request({
    url: '/system/geoAssess/' + assessId,
    method: 'get'
  })
}

// 新增评定表
export function addAssess(data) {
  return request({
    url: '/system/geoAssess',
    method: 'post',
    data: data
  })
}

// 修改评定表
export function updateAssess(data) {
  return request({
    url: '/system/geoAssess',
    method: 'put',
    data: data
  })
}

// 删除评定表
export function delAssess(assessId) {
  return request({
    url: '/system/geoAssess/' + assessId,
    method: 'delete'
  })
}

export function listAssessUser() {
    return request({
      url: '/system/geoAssess/listAssessUser',
      method: 'get',
    })
  }
