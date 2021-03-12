//
//  AddTransactionScreen.swift
//  iosApp (iOS)
//
//  Created by Marco Gomiero on 30/11/2020.
//

import SwiftUI
import shared

struct AddTransactionScreen: View {
    
    @Binding var showSheet: Bool
    @StateObject var addTransactionState = AddTransactionState()
    @State var showsDatePicker = false
    
    @ObservedObject var viewModel = AddTransactionViewModel()
    
    let dateFormatter: DateFormatter = {
        let df = DateFormatter()
        df.dateStyle = .medium
        return df
    }()
    
    var body: some View {
        NavigationView {
            VStack {
                
                Form {
                    
                    Picker("Transaction Type", selection: $viewModel.transactionTypeUI) {
                        ForEach(viewModel.transactions) {
                            Text($0.name).tag($0 as TransactionTypeRadioItem)
                        }
                    }
                    
                    HStack {
                        DMImage(imageName: "ic_euro_sign", color: Color.colorOnBackground)
                        TextField("0.00", text: $viewModel.amountTextField)
                            .keyboardType(.decimalPad)
                    }
                    
                    HStack {
                        DMImage(imageName: "ic_edit", color: Color.colorOnBackground)
                        TextField("Description", text: $viewModel.descriptionTextField)
                    }
                    
                    NavigationLink(destination: CategoriesScreen(addTransactionState: addTransactionState)) {
                        HStack {
                            DMImage(imageName: addTransactionState.categoryIcon ?? "ic_question_circle", color: Color.colorOnBackground)
                            Text(addTransactionState.categoryTitle ?? "Select Category")
                        }
                    }
                    
                    HStack {
                        DMImage(imageName: "ic_calendar", color: Color.colorOnBackground)
                        Text("\(dateFormatter.string(from: viewModel.transactionDate))")
                    }
                    .frame(minWidth: 0, maxWidth: .infinity, minHeight: 20, maxHeight: 20, alignment: .leading)
                    .contentShape(Rectangle())
                    .onTapGesture {
                        self.showsDatePicker.toggle()
                    }
                    
                    if showsDatePicker {
                        DatePicker("", selection: $viewModel.transactionDate, displayedComponents: .date)
                            .datePickerStyle(GraphicalDatePickerStyle())
                            .padding(20)
                    }
                }
            }
            .navigationTitle(Text("Add transaction"))
            .navigationBarItems(leading: Button(action: {
                self.showSheet.toggle()
            }) {
                // TODO: localize
                Text("Close")
                
            }, trailing: Button(  action: {
                self.viewModel.addTransaction()
                self.showSheet.toggle()
            }) {
                // TODO: localize
                Text("Save")
                
            }
                .disabled(viewModel.saveDisabled)
            )
            .onReceive(addTransactionState.$categoryId) { value in
                self.viewModel.categoryId = value
            }
        }

    }
}

//struct AddTransactionScreen_Previews: PreviewProvider {
//    static var previews: some View {
//        AddTransactionScreen()
//    }
//}
