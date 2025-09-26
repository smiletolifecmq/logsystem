import request from '@/utils/request'

// 查询福清分公司净利润申报表列表
export function listProfit(query) {
  return request({
    url: '/system/profit/list',
    method: 'get',
    params: query
  })
}

// 查询福清分公司净利润申报表详细
export function getProfit(id) {
  return request({
    url: '/system/profit/' + id,
    method: 'get'
  })
}

// 新增福清分公司净利润申报表
export function addProfit(data) {
  return request({
    url: '/system/profit',
    method: 'post',
    data: data
  })
}

// 修改福清分公司净利润申报表
export function updateProfit(data) {
  return request({
    url: '/system/profit',
    method: 'put',
    data: data
  })
}

// 删除福清分公司净利润申报表
export function delProfit(id) {
  return request({
    url: '/system/profit/' + id,
    method: 'delete'
  })
}

export function getlistProfitOne(query) {
  return request({
    url: '/system/profit/getlistProfitOne',
    method: 'get',
    params: query
  })
}