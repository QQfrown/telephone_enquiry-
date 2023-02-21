import request from '@/utils/request'

// 查询【请填写功能名称】列表
export function listInfo(query) {
  return request({
    url: '/tel/info/list',
    method: 'get',
    params: query
  })
}

//模糊查询
export function fuzzyQuery(query){
  return request({
    url:'/tel/info/fuzzyQuery',
    method:'get',
    params:query
  })
}

// 查询【请填写功能名称】详细
export function getInfo(id) {
  return request({
    url: '/tel/info/' + id,
    method: 'get'
  })
}

// 新增【请填写功能名称】
export function addInfo(data) {
  return request({
    url: '/tel/info',
    method: 'post',
    data: data
  })
}



// 修改【请填写功能名称】
export function updateInfo(data) {
  return request({
    url: '/tel/info',
    method: 'put',
    data: data
  })
}

// 删除【请填写功能名称】
export function delInfo(id) {
  return request({
    url: '/tel/info/' + id,
    method: 'delete'
  })
}
