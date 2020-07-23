import React from 'react';
import { DatePicker, Table } from 'antd';
const { RangePicker } = DatePicker;

const mainColumns = [
  {
    title: 'Reference ID',
    dataIndex: 'accountServicerRef',
  },
  {
    title: 'Status',
    dataIndex: 'status',
  },
  {
    title: 'Creditor Debit Indicator',
    dataIndex: 'creditDebitIndicator',
  },
  {
    title: 'Bank Transaction Code',
    dataIndex: 'bankTransactionCode',
  },
  {
    title: 'Value Date',
    dataIndex: 'valueDate',
  },
  {
    title: 'Booking Date',
    dataIndex: 'bookingDate',
  },
];

const TransactionsList = () => {
  const tempTransactions = [
    {
      amount: 'EUR:100.00',
      creditDebitIndicator: 'CRDT',
      status: 'BOOK',
      bankTransactionCode: 'PMNT-RCDT-ESCT', // look at first component (e.g payment/trade)
      valueDate: '2020-07-04', // when money moves
      bookingDate: '2020-07-02', // value on account
      accountServicerRef: 'acctsvcrref-001', // assigned by bank where you held acc
      details: {
        debtor: {
          name: 'Debtor One',
        },
        debtorAccount: {
          iban: 'DE52123456789473323175',
        },
        creditor: {
          name: 'Creditor One',
        },
        ultimateCreditor: {
          name: 'Ultimate Creditor One',
        },
        ultimateDebtor: {
          name: 'Ultimate Debtor One',
        },
        endToEndId: 'e2e-001', // assigned by person that starts payment
        purpose: 'GDDS', // trans related to purchase (set by payment initiator)
        unstructuredRemittanceInformation: 'unstructured info one',
      },
    },
    // {
    //   amount: 'EUR:50.00',
    //   creditDebitIndicator: 'CRDT',
    //   status: 'BOOK',
    //   bankTransactionCode: 'PMNT-RCDT-ESCT',
    //   valueDate: '2020-07-04',
    //   bookingDate: '2020-07-02',
    //   accountServicerRef: 'acctsvcrref-002',
    //   details: {
    //     debtor: {
    //       name: 'Debtor One',
    //     },
    //     debtorAccount: {
    //       iban: 'DE52123456789473323175',
    //     },
    //     creditor: {
    //       name: 'Creditor One',
    //     },
    //     endToEndId: 'e2e-002',
    //     unstructuredRemittanceInformation: 'unstructured info across lines',
    //   },
    // },
    // {
    //   amount: 'EUR:1.12',
    //   creditDebitIndicator: 'CRDT',
    //   status: 'BOOK',
    //   isRTransaction: true,
    //   bankTransactionCode: 'PMNT-ICDT-RRTN', // return transaction (e.g IBAN doesn't exist)
    //   valueDate: '2020-06-30',
    //   bookingDate: '2020-06-30',
    //   accountServicerRef: '2020063011423362000',
    //   details: {
    //     debtor: {
    //       name: 'Account Owner',
    //     },
    //     debtorAccount: {
    //       iban: 'DE54123456784713474163',
    //     },
    //     creditor: {
    //       name: 'Nonexistent Creditor',
    //     },
    //     creditorAccount: {
    //       iban: 'DE24500105177398216438',
    //     },
    //     endToEndId: 'NOTPROVIDED',
    //     unstructuredRemittanceInformation:
    //       'Retoure SEPA Ueberweisung vom 29.06.2020, Rueckgabegrund: AC01 IBAN fehlerhaft und ungültig SVWZ: RETURN, Sammelposten Nummer Zwei IBAN: DE24500105177398216438 BIC: INGDDEFFXXX', // truncate at some point in table column, show all in details section
    //     returnInfo: {
    //       originalBankTransactionCode: 'PMNT-ICDT-ESCT',
    //       originator: {
    //         organizationId: {
    //           bic: 'GENODEM1GLS',
    //         },
    //       },
    //       reason: 'AC01',
    //       additionalInfo: 'IBAN fehlerhaft und ungültig',
    //     },
    //   },
    // },
    // {
    //   amount: 'EUR:1000', // in currency of the account
    //   creditDebitIndicator: 'CRDT',
    //   status: 'BOOK',
    //   bankTransactionCode: 'PMNT-RCDT-XBCT', // cross currency bank xfer
    //   valueDate: '2020-07-04',
    //   bookingDate: '2020-07-03',
    //   accountServicerRef: 'acctsvcrref-002',
    //   details: {
    //     debtor: {
    //       name: 'Mr USA',
    //       postalAddress: {
    //         country: 'US',
    //         addressLines: ['42 Some Street', '4242 Somewhere'],
    //       },
    //     },
    //     debtorAccount: {
    //       otherId: {
    //         id: '9876543',
    //       },
    //     },
    //     debtorAgent: {
    //       bic: 'BANKUSNY', // show in details section
    //     },
    //     currencyExchange: {
    //       sourceCurrency: 'USD',
    //       targetCurrency: 'EUR',
    //       exchangeRate: '1.20', // depends on when currency switches over
    //     },
    //     instructedAmount: 'USD:1500', // party that initiated payment
    //     interBankSettlementAmount: 'EUR:1250.0', // used for cross currency xfer (amount that bank exchanges betweeen each other)
    //     counterValueAmount: 'EUR:1250.0', // amount before/after currency conversion before fees were applied
    //     unstructuredRemittanceInformation: 'Invoice No. 4242',
    //   },
    // },
    // {
    //   // ACH transaction (executes at the end of the day)/Most transactions are sent in real time now
    //   // Banks have inner transactions has a list inside the details view
    //   amount: 'EUR:48.42',
    //   creditDebitIndicator: 'DBIT',
    //   status: 'BOOK',
    //   bankTransactionCode: 'PMNT-ICDT-ESCT',
    //   valueDate: '2020-07-07',
    //   bookingDate: '2020-07-07',
    //   accountServicerRef: 'acctsvcrref-005',
    //   batches: [
    //     // one entry can have batches of transactions (collection)
    //     {
    //       batchTransactions: [
    //         // batch transaction should show as one entry and then clicking on the details section show all transactions inside it
    //         {
    //           amount: 'EUR:46.3',
    //           creditDebitIndicator: 'DBIT',
    //           details: {
    //             creditor: {
    //               name: 'Zahlungsempfaenger 23, ZA 5, DE',
    //               postalAddress: {
    //                 country: 'DE',
    //                 addressLines: ['DE Adresszeile 1', 'DE Adresszeile 2'],
    //               },
    //             },
    //             creditorAccount: {
    //               iban: 'DE32733516350012345678',
    //             },
    //             creditorAgent: {
    //               bic: 'BYLADEM1ALR',
    //             },
    //             unstructuredRemittanceInformation: '',
    //           },
    //         },
    //         {
    //           amount: 'EUR:46.3',
    //           creditDebitIndicator: 'DBIT',
    //           details: {
    //             creditor: {
    //               name: 'Zahlungsempfaenger 23, ZA 5, AT',
    //               postalAddress: {
    //                 country: 'AT',
    //                 addressLines: ['AT Adresszeile 1', 'AT Adresszeile 2'],
    //               },
    //             },
    //             creditorAccount: {
    //               iban: 'AT071100000012345678',
    //             },
    //             creditorAgent: {
    //               bic: 'BKAUATWW',
    //             },
    //             endToEndId: 'jh45k34h5l',
    //             paymentInformationId: '6j564l56',
    //             messageId: 'asdfasdf',
    //             unstructuredRemittanceInformation: '',
    //           },
    //         },
    //       ],
    //     },
    //   ],
    // },
  ];

  return (
    <>
      <div className="activity-buttons-row">
        <RangePicker />
      </div>
      <Table
        columns={mainColumns}
        dataSource={tempTransactions}
        expandable={{
          expandedRowRender: (record) => (
            <div>{JSON.stringify(record.details)}</div>
          ),
        }}
      />
    </>
  );
};

export default TransactionsList;