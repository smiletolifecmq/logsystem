import request from '@/utils/request'

// 查询应收账款列表
export function listCollection(query) {
  return request({
    url: '/system/collection/list',
    method: 'get',
    params: query
  })
}

// 查询应收账款详细
export function getCollection(ysId) {
  return request({
    url: '/system/collection/' + ysId,
    method: 'get'
  })
}

// 新增应收账款
export function addCollection(data) {
  return request({
    url: '/system/collection',
    method: 'post',
    data: data
  })
}

// 修改应收账款
export function updateCollection(data) {
  return request({
    url: '/system/collection',
    method: 'put',
    data: data
  })
}

// 删除应收账款
export function delCollection(ysId) {
  return request({
    url: '/system/collection/' + ysId,
    method: 'delete'
  })
}

export function listCollectionYdz(query) {
  return request({
    url: '/system/collection/ydz',
    method: 'get',
    params: query
  })
}

export function exportCs(query) {
  return request({
    url: '/system/collection/exportCs',
    method: 'get',
    params: query
  })
}

export function exportYs(query) {
  return request({
    url: '/system/collection/exportYs',
    method: 'get',
    params: query
  })
}

export function exportJyyb(query) {
  return request({
    url: '/system/collection/exportJyyb',
    method: 'get',
    params: query
  })
}

export function getExport(data) {
  return request({
    url: '/system/collection/getExport',
    method: 'post',
    data: data
  })
}

export function updateCollectionPl(data) {
  return request({
    url: '/system/collection/plxg',
    method: 'put',
    data: data
  })
}
