{
  "context": "List",
  "customerId": "${name}",
  "productCode":"CPCO",
  "filters": [
    {
      "key": "activeCode",
      "operator": "eq",
      "status": "${status}"
    }
  ],
  "pagination": {
    "pageSize": 50,
    "pageIndex": 5
  },
  "sorting": {
    "key": "created_on",
    "value": "asc"
  }
}