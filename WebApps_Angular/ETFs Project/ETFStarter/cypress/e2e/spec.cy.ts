// Testing - switching from 1 table to another
// button working
// has title
// shows 4 different types of button
// length of initial table = 4

describe('My First Test', () => {

  it('Visits the initial project page', () => {
    cy.visit('/')
    cy.contains('ETFs')
  })

  it('Contains headers', () => {
    cy.visit('')
    cy.get('h1').contains('ETFs')
  })

  it('Contains footers', () => {
    cy.visit('')
    cy.get('footer').contains('2023')
  })

  it('Contain 4 buttons', () => {
    cy.visit('')
    cy.get('button').should('have.length', 4);
  })

  it('Should display the length of Initial Table > 2', () => {
    cy.visit('')
    cy.get('tbody > tr').should('have.length.gt', 2)
  })

  it('Should switch between Table 1 to 2 successfully!', () => {
    cy.visit('')
    cy.get('button:contains("TR")').click();
    cy.get('table').should('be.visible', { timeout: 10000 });
    cy.get('table').find('tr').should('have.length.gt', 3);
  })

  it('Should switch between Table 2 to 4 successfully!', () => {
    cy.visit('')
    cy.get('button:contains("AUM")').click();
    cy.get('table').should('be.visible', { timeout: 10000 });
    cy.get('table').find('tr').should('have.length.gt', 3);
  })

  it('Should switch between Table 4 to 3 successfully!', () => {
    cy.visit('')
    cy.get('button:contains("ER")').click();
    cy.get('table').should('be.visible', { timeout: 10000 });
    cy.get('table').find('tr').should('have.length.gt', 3);
  })

})