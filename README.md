# ZJSON vs JSONPATCH
This project is to compare the output and performance of zjson and jsonPatch. This test includes following test data set includes:
1) Array of number, string, boolean or null
2) One Object
3) Array of objects
4) Highly nested Data
5) Array of highly nested Data

Origin: [ 100, 500, 300, 200, 400 ]
Target: [ 100, 500, 300, 200, 400, 500 ]
Op: add
zjsonPatch: [{"op":"add","path":"/5","value":500}]	1000:59.0	2000:13.0	5000:13.0
jsonPatch: [{"op":"copy","path":"/-","from":"/1"}]	1000:286.0	2000:25.0	5000:18.0
==================================
Origin: [ 100, 500, 300, 200, 400 ]
Target: [ 100, 500, 200, 200, 400 ]
Op: replace
zjsonPatch: [{"op":"remove","path":"/2"},{"op":"add","path":"/3","value":200}]	1000:12.0	2000:9.0	5000:9.0
jsonPatch: [{"op":"replace","path":"/2","value":200}]	1000:23.0	2000:15.0	5000:20.0
==================================
Origin: [ 100, 500, 300, 200, 400 ]
Target: [ 100, 500, 200, 400 ]
Op: remove
zjsonPatch: [{"op":"remove","path":"/2"}]	1000:11.0	2000:6.0	5000:6.0
jsonPatch: [{"op":"remove","path":"/4"},{"op":"replace","path":"/2","value":200},{"op":"replace","path":"/3","value":400}]	1000:24.0	2000:25.0	5000:27.0
==================================
############################
Origin: [ "This", "is","a","test"]
Target: [ "This", "is", "a", "json","test"]
Op: add
zjsonPatch: [{"op":"add","path":"/3","value":"json"}]	1000:10.0	2000:7.0	5000:7.0
jsonPatch: [{"op":"replace","path":"/3","value":"json"},{"op":"add","path":"/-","value":"test"}]	1000:25.0	2000:23.0	5000:28.0
==================================
Origin: [ "This", "is","a","test"]
Target: [ "This", "is", "a","rest"]
Op: replace
zjsonPatch: [{"op":"replace","path":"/3","value":"rest"}]	1000:7.0	2000:7.0	5000:3.0
jsonPatch: [{"op":"replace","path":"/3","value":"rest"}]	1000:15.0	2000:11.0	5000:14.0
==================================
Origin: [ "This", "is","a","test"]
Target: [ "This", "is", "test"]
Op: remove
zjsonPatch: [{"op":"remove","path":"/2"}]	1000:5.0	2000:3.0	5000:3.0
jsonPatch: [{"op":"remove","path":"/3"},{"op":"replace","path":"/2","value":"test"}]	1000:8.0	2000:9.0	5000:10.0
==================================
############################
Origin: [true, false]
Target: [true, true, false]
Op: add
zjsonPatch: [{"op":"add","path":"/1","value":true}]	1000:6.0	2000:4.0	5000:3.0
jsonPatch: [{"op":"replace","path":"/1","value":true},{"op":"add","path":"/-","value":false}]	1000:10.0	2000:9.0	5000:8.0
==================================
Origin: [true, false]
Target: [false, false]
Op: replace
zjsonPatch: [{"op":"remove","path":"/0"},{"op":"add","path":"/1","value":false}]	1000:4.0	2000:4.0	5000:4.0
jsonPatch: [{"op":"replace","path":"/0","value":false}]	1000:8.0	2000:6.0	5000:6.0
==================================
Origin: [true, false]
Target: [true]
Op: remove
zjsonPatch: [{"op":"remove","path":"/1"}]	1000:2.0	2000:2.0	5000:2.0
jsonPatch: [{"op":"remove","path":"/1"}]	1000:4.0	2000:4.0	5000:4.0
==================================
############################
Origin: [null, null]
Target: [null,null,null]
Op: add
zjsonPatch: [{"op":"add","path":"/2","value":null}]	1000:2.0	2000:2.0	5000:3.0
jsonPatch: [{"op":"copy","path":"/-","from":"/1"}]	1000:6.0	2000:6.0	5000:5.0
==================================
Origin: [null, null]
Target: [null]
Op: remove
zjsonPatch: [{"op":"remove","path":"/1"}]	1000:2.0	2000:2.0	5000:2.0
jsonPatch: [{"op":"remove","path":"/1"}]	1000:4.0	2000:5.0	5000:9.0
==================================
############################
Origin: {"_clientId":"ABCBank","_instanceId":"123"}
Target: {"_clientId":"ABCBank","_instanceId":"123","_ingest_ts":"1488938486000"}
Op: add
zjsonPatch: [{"op":"add","path":"/_ingest_ts","value":"1488938486000"}]	1000:4.0	2000:2.0	5000:2.0
jsonPatch: [{"op":"add","path":"/_ingest_ts","value":"1488938486000"}]	1000:24.0	2000:12.0	5000:9.0
==================================
Origin: {"_clientId":"ABCBank","_instanceId":"123"}
Target: {"_clientId":"ABKBank","_instancePd":"123"}
Op: replace
zjsonPatch: [{"op":"replace","path":"/_clientId","value":"ABKBank"},{"op":"move","path":"/_instancePd","from":"/_instanceId"}]	1000:5.0	2000:6.0	5000:5.0
jsonPatch: [{"op":"move","path":"/_instancePd","from":"/_instanceId"},{"op":"replace","path":"/_clientId","value":"ABKBank"}]	1000:12.0	2000:10.0	5000:10.0
==================================
Origin: {"_clientId":"ABCBank","_instanceId":"123"}
Target: {"_clientId":"ABCBank"}
Op: remove
zjsonPatch: [{"op":"remove","path":"/_instanceId"}]	1000:1.0	2000:1.0	5000:1.0
jsonPatch: [{"op":"remove","path":"/_instanceId"}]	1000:6.0	2000:7.0	5000:6.0
==================================
############################
Op: add
zjsonPatch: [{"op":"replace","path":"/","value":[{"dbFieldName":"encompass_id","value":"33334","type":"text"},{"dbFieldName":"base_loan_amount","value":"79","type":"decimal(28,10)"},{"dbFieldName":"modified_date","value":"2017-03-08T02:01:25Z","type":"timestamp"},{"dbFieldName":"created_date","value":"2009-02-03T19:08:00Z","type":"timestamp"},{"dbFieldName":"loan_id","type":"UNIQUEIDENTIFIER","value":"","autogenerated":"yes","primaryKey":"yes"},{"value":"","dbFieldName":"regulation_z_id","foreignKey":"RegulationZ.regulation_z_id:null","autogenerated":"yes"},{"value":"","dbFieldName":"property_id","foreignKey":"Property.property_id:null"},{"value":"","dbFieldName":"fha_va_loan_id","foreignKey":"FhaVaLoan.fha_va_loan_id:null"},{"value":"","dbFieldName":"down_payment_id","foreignKey":"DownPayment.down_payment_id:null"},{"value":"","dbFieldName":"mcaw_id","foreignKey":"Mcaw.mcaw_id:null"},{"value":"","dbFieldName":"fannie_mae_id","foreignKey":"FannieMae.fannie_mae_id:null"},{"value":"","dbFieldName":"disclosure_notices_id","foreignKey":"DisclosureNotices.disclosure_notices_id:null"},{"value":"","dbFieldName":"privacy_policy_id","foreignKey":"PrivacyPolicy.privacy_policy_id:null"},{"value":"","dbFieldName":"profit_management_id","foreignKey":"ProfitManagement.profit_management_id:null"},{"value":"","dbFieldName":".em_document_id","foreignKey":"EmDocument.em_document_id:null"},{"value":"","dbFieldName":"em_document_lender_id","foreignKey":"EmDocumentLender.em_document_lender_id:null"},{"value":"","dbFieldName":"em_document_investor_id","foreignKey":"EmDocumentInvestor.em_document_investor_id:null"},{"value":"","dbFieldName":"construction_management_id","foreignKey":"ConstructionManagement.construction_management_id:null"},{"dbFieldName":"newNode_id","foreignKey":"NewNodeTable.newNode_id:null"}]}]	1000:1.0	2000:1.0	5000:1.0
jsonPatch: [{"op":"replace","path":"","value":[{"dbFieldName":"encompass_id","value":"33334","type":"text"},{"dbFieldName":"base_loan_amount","value":"79","type":"decimal(28,10)"},{"dbFieldName":"modified_date","value":"2017-03-08T02:01:25Z","type":"timestamp"},{"dbFieldName":"created_date","value":"2009-02-03T19:08:00Z","type":"timestamp"},{"dbFieldName":"loan_id","type":"UNIQUEIDENTIFIER","value":"","autogenerated":"yes","primaryKey":"yes"},{"value":"","dbFieldName":"regulation_z_id","foreignKey":"RegulationZ.regulation_z_id:null","autogenerated":"yes"},{"value":"","dbFieldName":"property_id","foreignKey":"Property.property_id:null"},{"value":"","dbFieldName":"fha_va_loan_id","foreignKey":"FhaVaLoan.fha_va_loan_id:null"},{"value":"","dbFieldName":"down_payment_id","foreignKey":"DownPayment.down_payment_id:null"},{"value":"","dbFieldName":"mcaw_id","foreignKey":"Mcaw.mcaw_id:null"},{"value":"","dbFieldName":"fannie_mae_id","foreignKey":"FannieMae.fannie_mae_id:null"},{"value":"","dbFieldName":"disclosure_notices_id","foreignKey":"DisclosureNotices.disclosure_notices_id:null"},{"value":"","dbFieldName":"privacy_policy_id","foreignKey":"PrivacyPolicy.privacy_policy_id:null"},{"value":"","dbFieldName":"profit_management_id","foreignKey":"ProfitManagement.profit_management_id:null"},{"value":"","dbFieldName":".em_document_id","foreignKey":"EmDocument.em_document_id:null"},{"value":"","dbFieldName":"em_document_lender_id","foreignKey":"EmDocumentLender.em_document_lender_id:null"},{"value":"","dbFieldName":"em_document_investor_id","foreignKey":"EmDocumentInvestor.em_document_investor_id:null"},{"value":"","dbFieldName":"construction_management_id","foreignKey":"ConstructionManagement.construction_management_id:null"},{"dbFieldName":"newNode_id","foreignKey":"NewNodeTable.newNode_id:null"}]}]	1000:64.0	2000:51.0	5000:43.0
==================================
Op: replace
zjsonPatch: [{"op":"replace","path":"/","value":[{"dbFieldName":"encompass_id","value":"33334","type":"text"},{"dbFieldName":"base_loan_amount","value":"79","type":"decimal(28,10)"},{"dbFieldName":"modified_date","value":"2017-03-08T02:01:25Z","type":"timestamp"},{"dbFieldName":"created_date","value":"2009-02-03T19:08:00Z","type":"timestamp"},{"dbFieldName":"loan_id","type":"UNIQUEIDENTIFIER","value":"","autogenerated":"yes","primaryKey":"yes"},{"value":"","dbFieldName":"regulation_z_id","foreignKey":"RegulationZ.regulation_z_id:null"},{"value":"","dbFieldName":"property_id","foreignKey":"Property.property_id:null"},{"value":"","dbFieldName":"fha_va_loan_id","foreignKey":"FhaVaLoan.fha_va_loan_id:null"},{"value":"","dbFieldName":"down_payment_id","foreignKey":"DownPayment.down_payment_id:null"},{"value":"","dbFieldName":"mcaw_id","foreignKey":"Mcaw.mcaw_id:null"},{"value":"","dbFieldName":"fannie_mae_id","foreignKey":"FannieMae.fannie_mae_id:null"},{"value":"","dbFieldName":"disclosure_notices_id","foreignKey":"DisclosureNotices.disclosure_notices_id:null"},{"value":"12345","dbFieldName":"privacy_policy_id","foreignKey":"PrivacyPolicy.privacy_policy_id:null"},{"value":"","dbFieldName":"profit_management_id","foreignKey":"ProfitManagement.profit_management_id:null"},{"value":"","dbFieldName":".em_document_id","foreignKey":"EmDocument.em_document_id:null"},{"value":"","dbFieldName":"em_document_lender_id","foreignKey":"EmDocumentLender.em_document_lender_id:null"},{"value":"","dbFieldName":"em_document_investor_id","foreignKey":"EmDocumentInvestor.em_document_investor_id:null"},{"value":"","dbFieldName":"construction_management_id","foreignKey":"ConstructionManagement.construction_management_id:null"}]}]	1000:1.0	2000:1.0	5000:0.0
jsonPatch: [{"op":"replace","path":"","value":[{"dbFieldName":"encompass_id","value":"33334","type":"text"},{"dbFieldName":"base_loan_amount","value":"79","type":"decimal(28,10)"},{"dbFieldName":"modified_date","value":"2017-03-08T02:01:25Z","type":"timestamp"},{"dbFieldName":"created_date","value":"2009-02-03T19:08:00Z","type":"timestamp"},{"dbFieldName":"loan_id","type":"UNIQUEIDENTIFIER","value":"","autogenerated":"yes","primaryKey":"yes"},{"value":"","dbFieldName":"regulation_z_id","foreignKey":"RegulationZ.regulation_z_id:null"},{"value":"","dbFieldName":"property_id","foreignKey":"Property.property_id:null"},{"value":"","dbFieldName":"fha_va_loan_id","foreignKey":"FhaVaLoan.fha_va_loan_id:null"},{"value":"","dbFieldName":"down_payment_id","foreignKey":"DownPayment.down_payment_id:null"},{"value":"","dbFieldName":"mcaw_id","foreignKey":"Mcaw.mcaw_id:null"},{"value":"","dbFieldName":"fannie_mae_id","foreignKey":"FannieMae.fannie_mae_id:null"},{"value":"","dbFieldName":"disclosure_notices_id","foreignKey":"DisclosureNotices.disclosure_notices_id:null"},{"value":"12345","dbFieldName":"privacy_policy_id","foreignKey":"PrivacyPolicy.privacy_policy_id:null"},{"value":"","dbFieldName":"profit_management_id","foreignKey":"ProfitManagement.profit_management_id:null"},{"value":"","dbFieldName":".em_document_id","foreignKey":"EmDocument.em_document_id:null"},{"value":"","dbFieldName":"em_document_lender_id","foreignKey":"EmDocumentLender.em_document_lender_id:null"},{"value":"","dbFieldName":"em_document_investor_id","foreignKey":"EmDocumentInvestor.em_document_investor_id:null"},{"value":"","dbFieldName":"construction_management_id","foreignKey":"ConstructionManagement.construction_management_id:null"}]}]	1000:48.0	2000:47.0	5000:45.0
==================================
Op: remove
zjsonPatch: [{"op":"replace","path":"/","value":[{"dbFieldName":"encompass_id","value":"33334","type":"text"},{"dbFieldName":"base_loan_amount","value":"79","type":"decimal(28,10)"},{"dbFieldName":"modified_date","value":"2017-03-08T02:01:25Z","type":"timestamp"},{"dbFieldName":"created_date","value":"2009-02-03T19:08:00Z","type":"timestamp"},{"dbFieldName":"loan_id","type":"UNIQUEIDENTIFIER","value":"","autogenerated":"yes","primaryKey":"yes"},{"value":"","dbFieldName":"regulation_z_id","foreignKey":"RegulationZ.regulation_z_id:null"},{"value":"","dbFieldName":"property_id","foreignKey":"Property.property_id:null"},{"value":"","dbFieldName":"fha_va_loan_id","foreignKey":"FhaVaLoan.fha_va_loan_id:null"},{"value":"","dbFieldName":"down_payment_id","foreignKey":"DownPayment.down_payment_id:null"},{"value":"","dbFieldName":"mcaw_id","foreignKey":"Mcaw.mcaw_id:null"},{"value":"","dbFieldName":"fannie_mae_id","foreignKey":"FannieMae.fannie_mae_id:null"},{"value":"","dbFieldName":"disclosure_notices_id","foreignKey":"DisclosureNotices.disclosure_notices_id:null"},{"dbFieldName":"privacy_policy_id","foreignKey":"PrivacyPolicy.privacy_policy_id:null"},{"value":"","dbFieldName":"profit_management_id","foreignKey":"ProfitManagement.profit_management_id:null"},{"value":"","dbFieldName":".em_document_id","foreignKey":"EmDocument.em_document_id:null"},{"value":"","dbFieldName":"em_document_lender_id","foreignKey":"EmDocumentLender.em_document_lender_id:null"},{"value":"","dbFieldName":"em_document_investor_id","foreignKey":"EmDocumentInvestor.em_document_investor_id:null"},{"value":"","dbFieldName":"construction_management_id","foreignKey":"ConstructionManagement.construction_management_id:null"}]}]	1000:2.0	2000:1.0	5000:1.0
jsonPatch: [{"op":"replace","path":"","value":[{"dbFieldName":"encompass_id","value":"33334","type":"text"},{"dbFieldName":"base_loan_amount","value":"79","type":"decimal(28,10)"},{"dbFieldName":"modified_date","value":"2017-03-08T02:01:25Z","type":"timestamp"},{"dbFieldName":"created_date","value":"2009-02-03T19:08:00Z","type":"timestamp"},{"dbFieldName":"loan_id","type":"UNIQUEIDENTIFIER","value":"","autogenerated":"yes","primaryKey":"yes"},{"value":"","dbFieldName":"regulation_z_id","foreignKey":"RegulationZ.regulation_z_id:null"},{"value":"","dbFieldName":"property_id","foreignKey":"Property.property_id:null"},{"value":"","dbFieldName":"fha_va_loan_id","foreignKey":"FhaVaLoan.fha_va_loan_id:null"},{"value":"","dbFieldName":"down_payment_id","foreignKey":"DownPayment.down_payment_id:null"},{"value":"","dbFieldName":"mcaw_id","foreignKey":"Mcaw.mcaw_id:null"},{"value":"","dbFieldName":"fannie_mae_id","foreignKey":"FannieMae.fannie_mae_id:null"},{"value":"","dbFieldName":"disclosure_notices_id","foreignKey":"DisclosureNotices.disclosure_notices_id:null"},{"dbFieldName":"privacy_policy_id","foreignKey":"PrivacyPolicy.privacy_policy_id:null"},{"value":"","dbFieldName":"profit_management_id","foreignKey":"ProfitManagement.profit_management_id:null"},{"value":"","dbFieldName":".em_document_id","foreignKey":"EmDocument.em_document_id:null"},{"value":"","dbFieldName":"em_document_lender_id","foreignKey":"EmDocumentLender.em_document_lender_id:null"},{"value":"","dbFieldName":"em_document_investor_id","foreignKey":"EmDocumentInvestor.em_document_investor_id:null"},{"value":"","dbFieldName":"construction_management_id","foreignKey":"ConstructionManagement.construction_management_id:null"}]}]	1000:35.0	2000:34.0	5000:33.0
==================================
############################
Op: add
zjsonPatch: [{"op":"add","path":"/batters/batter/4","value":{"id":"1005","type":"Banana"}},{"op":"add","path":"/topping/7","value":{"id":"5005","type":"spicy"}},{"op":"add","path":"/new feature","value":"yes"}]	1000:26.0	2000:27.0	5000:14.0
jsonPatch: [{"op":"add","path":"/new feature","value":"yes"},{"op":"add","path":"/batters/batter/-","value":{"id":"1005","type":"Banana"}},{"op":"add","path":"/topping/-","value":{"id":"5005","type":"spicy"}}]	1000:117.0	2000:111.0	5000:105.0
==================================
Op: replace
zjsonPatch: [{"op":"replace","path":"/ppu","value":1.2},{"op":"replace","path":"/batters/batter/0/type","value":"No flavor"}]	1000:23.0	2000:14.0	5000:10.0
jsonPatch: [{"op":"replace","path":"/batters/batter/0/type","value":"No flavor"},{"op":"replace","path":"/ppu","value":1.2}]	1000:91.0	2000:83.0	5000:96.0
==================================
Op: remove
zjsonPatch: [{"op":"remove","path":"/ppu"},{"op":"remove","path":"/batters/batter/0"},{"op":"remove","path":"/topping/0"}]	1000:11.0	2000:9.0	5000:11.0
jsonPatch: [{"op":"remove","path":"/ppu"},{"op":"remove","path":"/batters/batter/3"},{"op":"replace","path":"/batters/batter/0/id","value":"1002"},{"op":"replace","path":"/batters/batter/0/type","value":"Chocolate"},{"op":"replace","path":"/batters/batter/1/id","value":"1003"},{"op":"replace","path":"/batters/batter/1/type","value":"Blueberry"},{"op":"replace","path":"/batters/batter/2/id","value":"1004"},{"op":"replace","path":"/batters/batter/2/type","value":"Devil's Food"},{"op":"remove","path":"/topping/6"},{"op":"replace","path":"/topping/0/id","value":"5002"},{"op":"replace","path":"/topping/0/type","value":"Glazed"},{"op":"replace","path":"/topping/1/id","value":"5005"},{"op":"replace","path":"/topping/1/type","value":"Sugar"},{"op":"replace","path":"/topping/2/id","value":"5007"},{"op":"replace","path":"/topping/2/type","value":"Powdered Sugar"},{"op":"replace","path":"/topping/3/id","value":"5006"},{"op":"replace","path":"/topping/3/type","value":"Chocolate with Sprinkles"},{"op":"replace","path":"/topping/4/id","value":"5003"},{"op":"replace","path":"/topping/4/type","value":"Chocolate"},{"op":"replace","path":"/topping/5/id","value":"5004"},{"op":"replace","path":"/topping/5/type","value":"Maple"}]	1000:129.0	2000:131.0	5000:121.0
==================================
############################
Op: add
zjsonPatch: [{"op":"add","path":"/0/batters/batter/4","value":{"id":"1005","type":"Banana"}},{"op":"add","path":"/0/topping/7","value":{"id":"5005","type":"spicy"}},{"op":"add","path":"/0/new feature","value":"yes"}]	1000:22.0	2000:22.0	5000:23.0
jsonPatch: [{"op":"add","path":"/0/new feature","value":"yes"},{"op":"add","path":"/0/batters/batter/-","value":{"id":"1005","type":"Banana"}},{"op":"add","path":"/0/topping/-","value":{"id":"5005","type":"spicy"}}]	1000:197.0	2000:174.0	5000:117.0
==================================
Op: replace
zjsonPatch: [{"op":"replace","path":"/0/ppu","value":1.2},{"op":"replace","path":"/0/batters/batter/0/type","value":"No flavor"}]	1000:18.0	2000:15.0	5000:12.0
jsonPatch: [{"op":"replace","path":"/0/batters/batter/0/type","value":"No flavor"},{"op":"replace","path":"/0/ppu","value":1.2}]	1000:62.0	2000:59.0	5000:57.0
==================================
Op: remove
zjsonPatch: [{"op":"remove","path":"/0/ppu"},{"op":"remove","path":"/0/batters/batter/0"},{"op":"remove","path":"/0/topping/0"}]	1000:13.0	2000:12.0	5000:12.0
jsonPatch: [{"op":"remove","path":"/0/ppu"},{"op":"remove","path":"/0/batters/batter/3"},{"op":"replace","path":"/0/batters/batter/0/id","value":"1002"},{"op":"replace","path":"/0/batters/batter/0/type","value":"Chocolate"},{"op":"replace","path":"/0/batters/batter/1/id","value":"1003"},{"op":"replace","path":"/0/batters/batter/1/type","value":"Blueberry"},{"op":"replace","path":"/0/batters/batter/2/id","value":"1004"},{"op":"replace","path":"/0/batters/batter/2/type","value":"Devil's Food"},{"op":"remove","path":"/0/topping/6"},{"op":"replace","path":"/0/topping/0/id","value":"5002"},{"op":"replace","path":"/0/topping/0/type","value":"Glazed"},{"op":"replace","path":"/0/topping/1/id","value":"5005"},{"op":"replace","path":"/0/topping/1/type","value":"Sugar"},{"op":"replace","path":"/0/topping/2/id","value":"5007"},{"op":"replace","path":"/0/topping/2/type","value":"Powdered Sugar"},{"op":"replace","path":"/0/topping/3/id","value":"5006"},{"op":"replace","path":"/0/topping/3/type","value":"Chocolate with Sprinkles"},{"op":"replace","path":"/0/topping/4/id","value":"5003"},{"op":"replace","path":"/0/topping/4/type","value":"Chocolate"},{"op":"replace","path":"/0/topping/5/id","value":"5004"},{"op":"replace","path":"/0/topping/5/type","value":"Maple"}]	1000:79.0	2000:84.0	5000:83.0
==================================
############################

