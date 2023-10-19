// Testing - switching from 1 table to another
// button working
// has title
// shows 4 different types of button
// length of initial table = 4

describe('My First Test', () => {

  it('Visits the initial project page', () => {
    cy.visit('/')
    cy.contains('Cars World')
  })

  it('Contains headers', () => {
    cy.visit('')
    cy.get('h1').contains('Cars World')
  })

  it('Contains footers', () => {
    cy.visit('')
    cy.get('footer').contains('2023')
  })

  it('Contain 4 buttons', () => {
    cy.visit('')
    cy.get('button').should('have.length.gt', 4);
  })

  it('Should display the length of Initial Table as 4', () => {
    cy.visit('')
    cy.get('tbody > tr').should('have.length', 4)
  })

  it('Should switch between Table 1 (Initial) to 2 (Price) successfully!', () => {
    cy.visit('')
    cy.get('button:contains("Price")').click();
    cy.get('table').should('be.visible', { timeout: 10000 });
    cy.get('table').find('tr').should('have.length.gt', 3);
  })

  it('Should switch between Table 2 (Price) to 4 (Doors) successfully!', () => {
    cy.visit('')
    cy.get('button:contains("Doors")').click();
    cy.get('table').should('be.visible', { timeout: 10000 });
    cy.get('table').find('tr').should('have.length.gt', 3);
  })

  it('Should switch between Table 4 (Doors) to 3 (Year) successfully!', () => {
    cy.visit('')
    cy.get('button:contains("Year")').click();
    cy.get('table').should('be.visible', { timeout: 10000 });
    cy.get('table').find('tr').should('have.length.gt', 3);
  })

})